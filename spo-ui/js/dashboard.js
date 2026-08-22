/**
 * ============================================================
 * PROCUREMENT DASHBOARD
 * ============================================================
 *
 * Handles:
 * - Authentication check
 * - Sidebar navigation
 * - Mobile sidebar
 * - Logout
 * - User information
 */


/* ============================================================
   AUTHENTICATION
   ============================================================ */

const accessToken =
    localStorage.getItem("accessToken");


/*
 * Uncomment this once login authentication is fully connected.
 *
 * if (!accessToken) {
 *     window.location.href = "login.html";
 * }
 */


/* ============================================================
   USER INFORMATION
   ============================================================ */

const currentUser =
    localStorage.getItem("currentUser");


if (currentUser) {

    try {

        const user =
            JSON.parse(currentUser);


        const userName =
            user.name ||
            user.fullName ||
            user.firstName;


        const userRole =
            user.role ||
            user.designation ||
            user.jobTitle;


        if (userName) {

            document.getElementById(
                "sidebarUserName"
            ).textContent = userName;

        }


        if (userRole) {

            document.getElementById(
                "sidebarUserRole"
            ).textContent = userRole;

        }

    } catch (error) {

        console.error(
            "Unable to parse current user:",
            error
        );

    }
}


/* ============================================================
   SIDEBAR
   ============================================================ */

const sidebar =
    document.getElementById("sidebar");

const sidebarToggle =
    document.getElementById("sidebarToggle");

const sidebarClose =
    document.getElementById("sidebarClose");

const sidebarOverlay =
    document.getElementById("sidebarOverlay");


function openSidebar() {

    sidebar.classList.add("open");

    sidebarOverlay.classList.add("visible");
}


function closeSidebar() {

    sidebar.classList.remove("open");

    sidebarOverlay.classList.remove("visible");
}


sidebarToggle.addEventListener(
    "click",
    openSidebar
);


sidebarClose.addEventListener(
    "click",
    closeSidebar
);


sidebarOverlay.addEventListener(
    "click",
    closeSidebar
);


/* ============================================================
   SIDEBAR NAVIGATION
   ============================================================ */

const sidebarLinks =
    document.querySelectorAll(".sidebar-link");


sidebarLinks.forEach((link) => {

    link.addEventListener("click", () => {

        sidebarLinks.forEach((item) => {
            item.classList.remove("active");
        });


        link.classList.add("active");


        if (window.innerWidth <= 900) {
            closeSidebar();
        }

    });

});


/* ============================================================
   LOGOUT
   ============================================================ */

const logoutButton =
    document.getElementById("sidebarLogout");


logoutButton.addEventListener(
    "click",
    () => {

        localStorage.removeItem(
            "accessToken"
        );

        localStorage.removeItem(
            "refreshToken"
        );

        localStorage.removeItem(
            "currentUser"
        );


        window.location.href =
            "login.html";

    }
);


/* ============================================================
   NOTIFICATIONS
   ============================================================ */

const notificationButton =
    document.getElementById(
        "notificationButton"
    );


notificationButton.addEventListener(
    "click",
    () => {

        alert(
            "You have 27 pending approvals."
        );

    }
);


/* ============================================================
   PROFILE
   ============================================================ */

const VIEW_PROFILE_URL = "http://localhost:8080/spo/api/v1/emp/view-profile";

async function loadUserProfile() {

    const token = localStorage.getItem("accessToken");

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    try {

        const response = await fetch(VIEW_PROFILE_URL, {
            method: "GET",
            headers: {
                "token": token, 
                "Content-Type": "application/json"
            }
        });

        if (response.status === 401 || response.status === 403) {

            localStorage.removeItem("accessToken");
            localStorage.removeItem("currentUser");

            window.location.href = "login.html";

            return;
        }

        if (!response.ok) {
            throw new Error(
                `Failed to fetch profile: ${response.status}`
            );
        }

        const employee = await response.json();

        console.log("Employee profile:", employee);

        localStorage.setItem(
        "currentUser",
        JSON.stringify(employee));

        updateDashboard(employee);
        
       

        applyPermissions(employee);

    } catch (error) {

        console.error(
            "Unable to load employee profile:",
            error
        );

    }
}

function updateDashboard(employee) {

    const fullName = getFullName(employee);

    document.getElementById(
        "sidebarUserName"
    ).textContent = fullName;

    document.getElementById(
        "welcomeUserName"
    ).textContent =
       fullName;

    document.getElementById(
        "sidebarUserRole"
    ).textContent =
        employee.designation ||
        employee.jobTitle ||
        "";

    updateProfileImage(employee);
}

function updateProfileImage(employee) {

    console.log("========== updateProfileImage START ==========");

    // 1. Check employee object
    console.log("Employee object:", employee);
    console.log("Employee JSON:", JSON.stringify(employee, null, 2));

    // 2. Check individual fields
    console.log("firstName:", employee?.firstName);
    console.log("middleName:", employee?.middleName);
    console.log("lastName:", employee?.lastName);
    console.log("profileImageUrl:", employee?.profileImageUrl);

    // 3. Find profile avatar element
    const avatar =
        document.getElementById("profileAvatar");

    console.log("profileAvatar element:", avatar);

    // 4. Find profile name element
    const profileName =
        document.getElementById("profileName");

    console.log("profileName element:", profileName);

    // 5. Check if elements actually exist
    if (!avatar) {
        console.error(
            "❌ profileAvatar NOT FOUND. Check HTML id='profileAvatar'"
        );
    } else {
        console.log(
            "✅ profileAvatar found:",
            avatar.outerHTML
        );
    }

    if (!profileName) {
        console.error(
            "❌ profileName NOT FOUND. Check HTML id='profileName'"
        );
    } else {
        console.log(
            "✅ profileName found:",
            profileName.outerHTML
        );
    }

    // Stop if profile name element doesn't exist
    if (!profileName) {
        console.error(
            "Stopping updateProfileImage because profileName is null"
        );
        return;
    }

    // 6. Generate full name
    const fullName =
        getFullName(employee);

    console.log("Generated full name:", fullName);

    // 7. Update name
    profileName.textContent = fullName;

    console.log(
        "Profile name after update:",
        profileName.textContent
    );

    // 8. Handle profile image
    if (employee?.profileImageUrl) {

        console.log(
            "Profile image URL exists:",
            employee.profileImageUrl
        );

        if (!avatar) {
            console.error(
                "Cannot update profile image because avatar element is missing"
            );
            return;
        }

        avatar.innerHTML = `
            <img
                src="${employee.profileImageUrl}"
                alt="Profile"
            >
        `;

        console.log(
            "Profile image HTML updated:",
            avatar.innerHTML
        );

    } else {

        console.log(
            "No profileImageUrl found. Using initials."
        );

        if (!avatar) {
            console.error(
                "Cannot update initials because avatar element is missing"
            );
            return;
        }

        const initials =
            getInitials(employee);

        console.log(
            "Generated initials:",
            initials
        );

        avatar.textContent = initials;

        console.log(
            "Avatar after initials update:",
            avatar.textContent
        );
    }

    console.log("========== updateProfileImage END ==========");
}

function getInitials(employee) {

    const first =
        employee.firstName?.charAt(0) || "";

    const last =
        employee.lastName?.charAt(0) || "";

    return `${first}${last}`.toUpperCase();
}

function getFullName(employee) {
    
    const firstName =
        employee.firstName || "";
    const lastName =
        employee.lastName || "";

    console.log(
        "Full name:",
        `${firstName} ${lastName}`.trim()
    );

    return `${firstName} ${lastName}`.trim();
}


function getRoleNames(employee) {

    return (employee.roles || [])
        .map(role => role.roleName)
        .filter(Boolean);
}

function getOperations(employee) {

    const operations = [];

    (employee.roles || []).forEach(role => {

        (role.operations || []).forEach(operation => {

            operations.push(operation);

        });

    });

    return operations;
}

function getUniqueOperations(employee) {

    const operations = getOperations(employee);

    return [
        ...new Map(
            operations.map(
                operation => [
                    operation.operationId,
                    operation
                ]
            )
        ).values()
    ];
}

function hasOperation(employee, operationName) {

    return getUniqueOperations(employee)
        .some(
            operation =>
                operation.operationName === operationName
        );
}

function applyPermissions(employee) {

    const permissionElements =
        document.querySelectorAll("[data-operation]");

    permissionElements.forEach((element) => {

        const requiredOperation =
            element.dataset.operation;

        if (!hasOperation(employee, requiredOperation)) {

            element.style.display = "none";

        }

    });
}

const profileButton =
    document.getElementById(
        "profileButton"
    );


profileButton.addEventListener(
    "click",
    () => {

        /*
         * Profile menu can be implemented here later.
         */

        console.log(
            "Profile menu clicked"
        );

    }
);


/* Call automatically when dashboard loads */
document.addEventListener("DOMContentLoaded", () => {
    loadUserProfile();
});
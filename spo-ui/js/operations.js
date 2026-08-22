/**
 * ============================================================
 * OPERATIONS
 * ============================================================
 *
 * Handles:
 * - Authentication
 * - Fetching application operations
 * - Rendering operations
 * - Logout
 * - Sidebar
 */

const OPERATIONS_API =
    "http://localhost:8080/base/api/v1/operation/get/all";


/* ============================================================
   AUTHENTICATION
   ============================================================ */

const accessToken =
    localStorage.getItem("accessToken");

if (!accessToken) {

    window.location.href = "login.html";

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


if (sidebarToggle) {

    sidebarToggle.addEventListener(
        "click",
        openSidebar
    );

}


if (sidebarClose) {

    sidebarClose.addEventListener(
        "click",
        closeSidebar
    );

}


if (sidebarOverlay) {

    sidebarOverlay.addEventListener(
        "click",
        closeSidebar
    );

}


/* ============================================================
   USER INFORMATION
   ============================================================ */

function loadCurrentUser() {

    const currentUser =
        localStorage.getItem("currentUser");

    if (!currentUser) {
        return;
    }

    try {

        const employee =
            JSON.parse(currentUser);

        const fullName =
            `${employee.firstName || ""} ${employee.lastName || ""}`
                .trim();

        const nameElement =
            document.getElementById("sidebarUserName");

        const roleElement =
            document.getElementById("sidebarUserRole");

        if (nameElement && fullName) {

            nameElement.textContent =
                fullName;

        }

        if (roleElement) {

            roleElement.textContent =
                employee.designation ||
                employee.jobTitle ||
                "";

        }

    } catch (error) {

        console.error(
            "Unable to parse current user:",
            error
        );

    }

}


/* ============================================================
   FETCH OPERATIONS
   ============================================================ */

async function loadOperations() {

    const loadingElement =
        document.getElementById(
            "operationsLoading"
        );

    const errorElement =
        document.getElementById(
            "operationsError"
        );

    const container =
        document.getElementById(
            "operationsContainer"
        );

    try {

        loadingElement.style.display =
            "block";

        errorElement.style.display =
            "none";

        container.style.display =
            "none";


        const token =
            localStorage.getItem(
                "accessToken"
            );


        if (!token) {

            window.location.href =
                "login.html";

            return;
        }


        const response =
            await fetch(
                OPERATIONS_API,
                {
                    method: "GET",

                    headers: {
                        "token": token,
                        "Content-Type":
                            "application/json"
                    }
                }
            );


        /* ============================================
           UNAUTHORIZED
           ============================================ */

        if (
            response.status === 401 ||
            response.status === 403
        ) {

            errorElement.textContent =
                "You are not authorized to view operations.";

            errorElement.style.display =
                "block";

            loadingElement.style.display =
                "none";

            return;
        }


        /* ============================================
           OTHER API ERRORS
           ============================================ */

        if (!response.ok) {

            throw new Error(
                `Failed to fetch operations: ${response.status}`
            );

        }


        const operations =
            await response.json();


        console.log(
            "Application operations:",
            operations
        );


        renderOperations(
            operations
        );


    } catch (error) {

        console.error(
            "Unable to load operations:",
            error
        );

        loadingElement.style.display =
            "none";

        errorElement.textContent =
            "Unable to load operations. Please try again.";

        errorElement.style.display =
            "block";

    }

}


/* ============================================================
   RENDER OPERATIONS
   ============================================================ */

function renderOperations(operations) {

    const loadingElement =
        document.getElementById(
            "operationsLoading"
        );

    const container =
        document.getElementById(
            "operationsContainer"
        );

    const tableBody =
        document.getElementById(
            "operationsTableBody"
        );

    const operationCount =
        document.getElementById(
            "operationCount"
        );


    tableBody.innerHTML = "";


    if (
        !Array.isArray(operations) ||
        operations.length === 0
    ) {

        tableBody.innerHTML = `
            <tr>
                <td colspan="3">
                    No operations found.
                </td>
            </tr>
        `;

    } else {

        operations.forEach(
            operation => {

                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>
                        <span class="operation-id">
                            ${escapeHtml(
                                operation.operationId || "-"
                            )}
                        </span>
                    </td>

                    <td>
                        <strong>
                            ${escapeHtml(
                                operation.operationName || "-"
                            )}
                        </strong>
                    </td>

                    <td>
                        <span class="operation-category">
                            ${escapeHtml(
                                operation.operationCategory || "-"
                            )}
                        </span>
                    </td>

                `;


                tableBody.appendChild(row);

            }
        );

    }


    operationCount.textContent =
        Array.isArray(operations)
            ? operations.length
            : 0;


    loadingElement.style.display =
        "none";

    container.style.display =
        "block";

}


/* ============================================================
   HTML ESCAPING
   ============================================================ */

function escapeHtml(value) {

    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");

}


/* ============================================================
   LOGOUT
   ============================================================ */

const logoutButton =
    document.getElementById(
        "sidebarLogout"
    );


if (logoutButton) {

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

}


/* ============================================================
   INITIALIZE
   ============================================================ */

document.addEventListener(
    "DOMContentLoaded",
    () => {

        loadCurrentUser();

        loadOperations();

    }
);
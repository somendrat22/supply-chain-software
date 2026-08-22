/**
 * Employee Login
 *
 * Handles:
 * - Employee authentication
 * - Password visibility
 * - Loading state
 * - API errors
 * - Token storage
 */


// ============================================================
// API CONFIGURATION
// ============================================================

const LOGIN_API_URL = "http://localhost:8080/spo/api/v1/emp/login";


// ============================================================
// DOM ELEMENTS
// ============================================================

const loginForm = document.getElementById("loginForm");

const emailInput = document.getElementById("email");
const passwordInput = document.getElementById("password");

const loginButton = document.getElementById("loginButton");
const loginButtonText =
    document.getElementById("loginButtonText");

const loginError =
    document.getElementById("loginError");

const togglePassword =
    document.getElementById("togglePassword");


// ============================================================
// PASSWORD VISIBILITY
// ============================================================

togglePassword.addEventListener("click", () => {

    const isPassword =
        passwordInput.type === "password";

    passwordInput.type =
        isPassword ? "text" : "password";

    togglePassword.textContent =
        isPassword ? "Hide" : "Show";
});


// ============================================================
// ERROR HANDLING
// ============================================================

function showLoginError(message) {

    loginError.textContent = message;

    loginError.classList.add("visible");
}


function clearLoginError() {

    loginError.textContent = "";

    loginError.classList.remove("visible");
}


// ============================================================
// LOADING STATE
// ============================================================

function setLoginLoading(isLoading) {

    loginButton.disabled = isLoading;

    if (isLoading) {

        loginButtonText.innerHTML = `
            <span class="login-spinner"></span>
            Signing in...
        `;

        return;
    }

    loginButtonText.textContent = "Sign in";
}


// ============================================================
// LOGIN
// ============================================================

loginForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    clearLoginError();


    const email =
        emailInput.value.trim();

    const password =
        passwordInput.value;


    if (!email || !password) {

        showLoginError(
            "Please enter your work email and password."
        );

        return;
    }


    setLoginLoading(true);


    try {

        const response = await fetch(
            LOGIN_API_URL,
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },

                body: JSON.stringify({
                    email,
                    password
                })
            }
        );


        let responseData = null;

        try {

            responseData =
                await response.json();

        } catch (error) {

            responseData = null;
        }


        // ====================================================
        // AUTHENTICATION FAILURE
        // ====================================================

        if (!response.ok) {

            const message =
                responseData?.message ||
                responseData?.error ||
                responseData?.detail ||
                "Invalid email or password.";

            showLoginError(message);

            return;
        }


        // ====================================================
        // AUTHENTICATION SUCCESS
        // ====================================================

        const accessToken =
            responseData?.accessToken ||
            responseData?.token ||
            responseData?.jwt ||
            responseData?.jwtToken;


        if (accessToken) {

            localStorage.setItem(
                "accessToken",
                accessToken
            );
        }


        if (responseData?.refreshToken) {

            localStorage.setItem(
                "refreshToken",
                responseData.refreshToken
            );
        }


        if (responseData?.user) {

            localStorage.setItem(
                "currentUser",
                JSON.stringify(
                    responseData.user
                )
            );
        }


        // ====================================================
        // REDIRECT
        // ====================================================

        window.location.href =
            "dashboard.html";

    } catch (error) {

        console.error(
            "Login request failed:",
            error
        );

        showLoginError(
            "Unable to connect to the server. Please try again."
        );

    } finally {

        setLoginLoading(false);
    }
});


// ============================================================
// FORGOT PASSWORD
// ============================================================

document
    .getElementById("forgotPassword")
    .addEventListener("click", (event) => {

        event.preventDefault();

        showLoginError(
            "Please contact your administrator to reset your password."
        );
    });
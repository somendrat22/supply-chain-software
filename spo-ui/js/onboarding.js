const API_BASE_URL = "http://localhost:8080";

const ONBOARDING_ENDPOINT =
    "/spo/api/v1/procurement-company/on-board";


let currentStep = 1;

const totalSteps = 5;


const form =
    document.getElementById("onboardingForm");

const steps =
    document.querySelectorAll(".form-step");

const progressSteps =
    document.querySelectorAll(".progress-step");

const nextBtn =
    document.getElementById("nextBtn");

const previousBtn =
    document.getElementById("previousBtn");

const submitBtn =
    document.getElementById("submitBtn");

const review =
    document.getElementById("review");

const submitError =
    document.getElementById("submitError");


/* =========================================================
   INITIALIZATION
========================================================= */

document.addEventListener(
    "DOMContentLoaded",
    () => {

        updateUI();

    }
);


/* =========================================================
   NEXT
========================================================= */

nextBtn.addEventListener(
    "click",
    () => {

        if (!validateCurrentStep()) {
            return;
        }

        if (currentStep < totalSteps) {

            currentStep++;

            if (currentStep === totalSteps) {
                buildReview();
            }

            updateUI();

        }

    }
);


/* =========================================================
   PREVIOUS
========================================================= */

previousBtn.addEventListener(
    "click",
    () => {

        if (currentStep > 1) {

            currentStep--;

            updateUI();

        }

    }
);


/* =========================================================
   SUBMIT
========================================================= */

form.addEventListener(
    "submit",
    async (event) => {

        event.preventDefault();

        if (!validateCurrentStep()) {
            return;
        }

        await submitProcurementCompany();

    }
);


/* =========================================================
   UPDATE UI
========================================================= */

function updateUI() {

    steps.forEach(
        step => {

            const stepNumber =
                Number(step.dataset.step);

            step.classList.toggle(
                "active",
                stepNumber === currentStep
            );

        }
    );


    progressSteps.forEach(
        (step, index) => {

            const stepNumber =
                index + 1;

            step.classList.remove(
                "active",
                "completed"
            );


            if (stepNumber === currentStep) {

                step.classList.add("active");

            } else if (stepNumber < currentStep) {

                step.classList.add("completed");

            }

        }
    );


    previousBtn.classList.toggle(
        "hidden",
        currentStep === 1
    );


    nextBtn.classList.toggle(
        "hidden",
        currentStep === totalSteps
    );


    submitBtn.classList.toggle(
        "hidden",
        currentStep !== totalSteps
    );


    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });

}


/* =========================================================
   VALIDATION
========================================================= */

function validateCurrentStep() {

    const currentSection =
        document.querySelector(
            `.form-step[data-step="${currentStep}"]`
        );


    const requiredFields =
        currentSection.querySelectorAll(
            "[required]"
        );


    let valid = true;


    requiredFields.forEach(
        field => {

            field.classList.remove("invalid");


            if (!field.value.trim()) {

                field.classList.add("invalid");

                valid = false;

                return;

            }


            if (
                field.type === "email" &&
                !isValidEmail(field.value)
            ) {

                field.classList.add("invalid");

                valid = false;

            }


            if (
                field.type === "url" &&
                field.value &&
                !isValidUrl(field.value)
            ) {

                field.classList.add("invalid");

                valid = false;

            }

        }
    );


    if (!valid) {

        const firstInvalid =
            currentSection.querySelector(
                ".invalid"
            );

        if (firstInvalid) {
            firstInvalid.focus();
        }

    }


    return valid;

}


/* =========================================================
   EMAIL VALIDATION
========================================================= */

function isValidEmail(email) {

    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        .test(email);

}


/* =========================================================
   URL VALIDATION
========================================================= */

function isValidUrl(value) {

    try {

        new URL(value);

        return true;

    } catch {

        return false;

    }

}


/* =========================================================
   BUILD REQUEST PAYLOAD
========================================================= */

function buildPayload() {

    const formData =
        new FormData(form);


    return {

        procurementCompanyCode:
            getValue(
                formData,
                "procurementCompanyCode"
            ),

        procurementModel:
            getValue(
                formData,
                "procurementModel"
            ),

        procurementStrategy:
            getValue(
                formData,
                "procurementStrategy"
            ),

        annualProcurementBudget:
            getNumber(
                formData,
                "annualProcurementBudget"
            ),

        annualProcurementSpend:
            getNumber(
                formData,
                "annualProcurementSpend"
            ),

        defaultCurrency:
            getValue(
                formData,
                "defaultCurrency"
            ),

        budgetControlEnabled:
            getBoolean(
                formData,
                "budgetControlEnabled"
            ),

        purchaseRequisitionRequired:
            getBoolean(
                formData,
                "purchaseRequisitionRequired"
            ),

        purchaseOrderRequired:
            getBoolean(
                formData,
                "purchaseOrderRequired"
            ),

        sourcingRequired:
            getBoolean(
                formData,
                "sourcingRequired"
            ),

        contractRequired:
            getBoolean(
                formData,
                "contractRequired"
            ),

        supplierApprovalRequired:
            getBoolean(
                formData,
                "supplierApprovalRequired"
            ),

        multiLevelApprovalEnabled:
            getBoolean(
                formData,
                "multiLevelApprovalEnabled"
            ),

        threeWayMatchingEnabled:
            getBoolean(
                formData,
                "threeWayMatchingEnabled"
            ),

        legalName:
            getValue(
                formData,
                "legalName"
            ),

        displayName:
            getValue(
                formData,
                "displayName"
            ),

        companyCode:
            getValue(
                formData,
                "companyCode"
            ),

        registrationNumber:
            getValue(
                formData,
                "registrationNumber"
            ),

        companyType:
            getValue(
                formData,
                "companyType"
            ),

        industry:
            getValue(
                formData,
                "industry"
            ),

        businessDescription:
            getValue(
                formData,
                "businessDescription"
            ),

        website:
            getValue(
                formData,
                "website"
            ),

        email:
            getValue(
                formData,
                "email"
            ),

        phoneNumber:
            getValue(
                formData,
                "phoneNumber"
            ),

        registeredAddress:
            getValue(
                formData,
                "registeredAddress"
            ),

        taxIdentificationNumber:
            getValue(
                formData,
                "taxIdentificationNumber"
            ),

        vatNumber:
            getValue(
                formData,
                "vatNumber"
            ),

        gstNumber:
            getValue(
                formData,
                "gstNumber"
            )

    };

}


/* =========================================================
   FORM DATA HELPERS
========================================================= */

function getValue(
    formData,
    name
) {

    return (
        formData.get(name) || ""
    ).toString().trim();

}


function getNumber(
    formData,
    name
) {

    const value =
        formData.get(name);

    if (
        value === null ||
        value === ""
    ) {
        return 0;
    }

    return Number(value);

}


function getBoolean(
    formData,
    name
) {

    return formData.get(name) === "on";

}


/* =========================================================
   REVIEW
========================================================= */

function buildReview() {

    const payload =
        buildPayload();


    review.innerHTML = "";


    addReviewSection(
        "PROCUREMENT CONFIGURATION"
    );


    addReviewItem(
        "Procurement company code",
        payload.procurementCompanyCode
    );


    addReviewItem(
        "Procurement model",
        payload.procurementModel
    );


    addReviewItem(
        "Procurement strategy",
        payload.procurementStrategy
    );


    addReviewItem(
        "Default currency",
        payload.defaultCurrency
    );


    addReviewItem(
        "Annual procurement budget",
        formatCurrency(
            payload.annualProcurementBudget,
            payload.defaultCurrency
        )
    );


    addReviewItem(
        "Annual procurement spend",
        formatCurrency(
            payload.annualProcurementSpend,
            payload.defaultCurrency
        )
    );


    addReviewSection(
        "PROCUREMENT CONTROLS"
    );


    addReviewItem(
        "Budget control",
        yesNo(payload.budgetControlEnabled)
    );


    addReviewItem(
        "Purchase requisition",
        yesNo(payload.purchaseRequisitionRequired)
    );


    addReviewItem(
        "Purchase order",
        yesNo(payload.purchaseOrderRequired)
    );


    addReviewItem(
        "Sourcing",
        yesNo(payload.sourcingRequired)
    );


    addReviewItem(
        "Contract",
        yesNo(payload.contractRequired)
    );


    addReviewItem(
        "Supplier approval",
        yesNo(payload.supplierApprovalRequired)
    );


    addReviewItem(
        "Multi-level approval",
        yesNo(payload.multiLevelApprovalEnabled)
    );


    addReviewItem(
        "Three-way matching",
        yesNo(payload.threeWayMatchingEnabled)
    );


    addReviewSection(
        "COMPANY INFORMATION"
    );


    addReviewItem(
        "Legal name",
        payload.legalName
    );


    addReviewItem(
        "Display name",
        payload.displayName
    );


    addReviewItem(
        "Company code",
        payload.companyCode
    );


    addReviewItem(
        "Registration number",
        payload.registrationNumber
    );


    addReviewItem(
        "Company type",
        payload.companyType
    );


    addReviewSection(
        "BUSINESS INFORMATION"
    );


    addReviewItem(
        "Industry",
        payload.industry
    );


    addReviewItem(
        "Website",
        payload.website || "Not provided"
    );


    addReviewItem(
        "Business description",
        payload.businessDescription
    );


    addReviewSection(
        "CONTACT & LEGAL"
    );


    addReviewItem(
        "Email",
        payload.email
    );


    addReviewItem(
        "Phone number",
        payload.phoneNumber
    );


    addReviewItem(
        "Registered address",
        payload.registeredAddress
    );


    addReviewItem(
        "Tax identification number",
        payload.taxIdentificationNumber ||
            "Not provided"
    );


    addReviewItem(
        "VAT number",
        payload.vatNumber ||
            "Not provided"
    );


    addReviewItem(
        "GST number",
        payload.gstNumber ||
            "Not provided"
    );

}


/* =========================================================
   REVIEW HELPERS
========================================================= */

function addReviewSection(title) {

    const section =
        document.createElement("div");

    section.className =
        "review-section";

    section.textContent =
        title;

    review.appendChild(section);

}


function addReviewItem(
    label,
    value
) {

    const item =
        document.createElement("div");

    item.className =
        "review-item";


    const small =
        document.createElement("small");

    small.textContent =
        label;


    const strong =
        document.createElement("strong");

    strong.textContent =
        value || "Not provided";


    item.appendChild(small);

    item.appendChild(strong);

    review.appendChild(item);

}


/* =========================================================
   FORMATTING
========================================================= */

function yesNo(value) {

    return value
        ? "Enabled"
        : "Disabled";

}


function formatCurrency(
    value,
    currency
) {

    if (
        value === null ||
        value === undefined ||
        value === ""
    ) {
        return "Not provided";
    }


    try {

        return new Intl.NumberFormat(
            "en-IN",
            {
                style: "currency",
                currency: currency || "INR",
                maximumFractionDigits: 2
            }
        ).format(value);

    } catch {

        return value;

    }

}


/* =========================================================
   SUBMIT TO BACKEND
========================================================= */

async function submitProcurementCompany() {

    const payload =
        buildPayload();


    submitBtn.disabled = true;

    submitBtn.textContent =
        "Creating company...";


    submitError.classList.add(
        "hidden"
    );


    try {

        const response =
            await fetch(
                API_BASE_URL +
                ONBOARDING_ENDPOINT,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json",

                        "Accept":
                            "application/json"
                    },

                    body:
                        JSON.stringify(payload)
                }
            );


        if (!response.ok) {

            let message =
                "Unable to create procurement company.";

            try {

                const error =
                    await response.json();

                message =
                    error.message ||
                    error.error ||
                    message;

            } catch {
                // Backend did not return JSON.
            }

            throw new Error(message);

        }


        const result =
            await response.json();


        showSuccess(
            result,
            payload
        );


    } catch (error) {

        console.error(
            "Procurement company onboarding failed:",
            error
        );


        submitError.textContent =
            error.message ||
            "Something went wrong while creating the procurement company.";


        submitError.classList.remove(
            "hidden"
        );


        submitBtn.disabled = false;

        submitBtn.textContent =
            "Create procurement company";

    }

}


/* =========================================================
   SUCCESS
========================================================= */

function showSuccess(
    result,
    payload
) {

    const generatedCode =
        result?.procurementCompanyCode ||
        result?.companyCode ||
        payload.procurementCompanyCode;


    const formStep =
        document.querySelector(
            '.form-step[data-step="5"]'
        );


    formStep.innerHTML = `

        <div class="success-card">

            <div class="success-icon">
                ✓
            </div>

            <h2>
                Procurement company created
            </h2>

            <p>
                Your procurement company has been successfully
                created and is now available in the
                Sourcing & Procurement application.
            </p>

            <span class="company-code">
                ${escapeHtml(generatedCode)}
            </span>

        </div>

    `;


    nextBtn.classList.add(
        "hidden"
    );

    submitBtn.classList.add(
        "hidden"
    );

    previousBtn.classList.add(
        "hidden"
    );


    progressSteps.forEach(
        step => {

            step.classList.remove(
                "active"
            );

            step.classList.add(
                "completed"
            );

        }
    );

}


/* =========================================================
   HTML ESCAPE
========================================================= */

function escapeHtml(value) {

    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");

}
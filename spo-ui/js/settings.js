document.addEventListener("DOMContentLoaded", () => {

    const currentUser =
        localStorage.getItem("currentUser");

    if (!currentUser) {
        window.location.href = "login.html";
        return;
    }

    const employee = JSON.parse(currentUser);

    const operations = (employee.roles || [])
        .flatMap(role => role.operations || []);

    const hasOperation = (operationName) =>
        operations.some(
            operation =>
                operation.operationName === operationName
        );

    document
        .querySelectorAll("[data-operation]")
        .forEach(element => {

            const requiredOperation =
                element.dataset.operation;

            if (!hasOperation(requiredOperation)) {
                element.style.display = "none";
            }

        });

});
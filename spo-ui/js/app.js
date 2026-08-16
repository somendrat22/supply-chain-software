document.addEventListener("DOMContentLoaded", () => {

    const menuToggle = document.getElementById("menuToggle");
    const navLinks = document.getElementById("navLinks");

    /*
     * Mobile navigation
     */
    menuToggle.addEventListener("click", () => {

        navLinks.classList.toggle("open");

        const expanded =
            navLinks.classList.contains("open");

        menuToggle.setAttribute(
            "aria-expanded",
            expanded
        );
    });


    /*
     * Close mobile menu after navigation
     */
    document.querySelectorAll("#navLinks a")
        .forEach(link => {

            link.addEventListener("click", () => {

                navLinks.classList.remove("open");

                menuToggle.setAttribute(
                    "aria-expanded",
                    "false"
                );

            });

        });


    /*
     * Workflow step interaction
     */
    const steps =
        document.querySelectorAll(".step");

    steps.forEach(step => {

        step.addEventListener("click", () => {

            steps.forEach(item =>
                item.classList.remove("active")
            );

            step.classList.add("active");

        });

    });


    /*
     * Smooth scrolling
     */
    document.querySelectorAll('a[href^="#"]')
        .forEach(anchor => {

            anchor.addEventListener("click", event => {

                const targetId =
                    anchor.getAttribute("href");

                if (
                    targetId === "#" ||
                    targetId === ""
                ) {
                    return;
                }

                const target =
                    document.querySelector(targetId);

                if (!target) {
                    return;
                }

                event.preventDefault();

                target.scrollIntoView({
                    behavior: "smooth",
                    block: "start"
                });

            });

        });

});
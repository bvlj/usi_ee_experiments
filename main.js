// Fragments
const FRAGMENT_MAIN = "_main";
const FRAGMENT_WELCOME = "_welcome";
const FRAGMENT_DEMO = "_demo";
const FRAGMENT_INTRO = "_intro";
const FRAGMENT_EXPERIMENT = "_experiment";
const FRAGMENT_DONE = "_done";

/* *** Utils *** */
function randomSort(list) {
    return [...list].sort((a, b) => 0.5 - Math.random())
}

function pickRandom(list, to_pick) {
    return randomSort(list).slice(0, to_pick)
}

function overlaps(a, b) {
    const l1 = {
        x: a.left,
        y: a.top
    };
    const r1 = {
        x: a.left + a.width,
        y: a.top + a.height
    };
    const l2 = {
        x: b.left,
        y: b.top
    };
    const r2 = {
        x: b.left + b.width,
        y: b.top + b.height
    };

    return !(l1.x >= r2.x || l2.x >= r1.x || l1.y <= r2.y || l2.y <= r1.y);
}

function match(a, b) {
    const aa = a.replaceAll(/(-)|( )/g, "").toLowerCase();
    const bb = b.replaceAll(/(-)|( )/g, "").toLowerCase();
    return aa === bb;
}

function onWelcomeFormSubmitted(form) {
    const isAdvanced = form["is_advanced"].value === "true";
    const userData = {
        "advanced": isAdvanced,
        "results": [],
    };
    moveToDemo(userData);
}

function setupTestCanvas(container, words, onSelected) {
    const containerRect = container.getBoundingClientRect();
    const min = {
        x: containerRect.left,
        y: containerRect.top
    };
    const max = {
        x: containerRect.right,
        y: containerRect.bottom
    };
    const placedBoxes = [];

    container.innerHTML = "";
    words.forEach(word => {
        const p = document.createElement("p");
        p.className = "word";
        p.style.opacity = "0";
        p.innerText = word;
        p.addEventListener("click", _ => onSelected(word));
        container.appendChild(p);

        const pRect = p.getBoundingClientRect();

        let conflicts = true;
        while (conflicts) {
            conflicts = false;
            const left = Math.round((Math.random() * max.x)) + "px";
            const top = Math.round((Math.random() * max.y) + min.y) + "px";

            if (left > max.x - pRect.width - 50 || top > max.y - pRect.height - 50) {
                continue;
            }

            const box = {top: top, left: left, width: pRect.width, height: pRect.height};
            for (let i = 0; i < placedBoxes.length; i++) {
                if (overlaps(box, placedBoxes[i])) {
                    conflicts = true
                    break
                }
            }

            if (!conflicts) {
                placedBoxes.push(box);
                p.style.position = "absolute";
                p.style.left = left;
                p.style.top = top;
                p.style.opacity = "1";
            }
        }
    });
}

/* *** Flow *** */

function moveToMain() {
    setActiveFragment(FRAGMENT_MAIN);
}

function moveToWelcome() {
    setActiveFragment(FRAGMENT_WELCOME);
    const form = document.forms["welcome_form"];
    form.addEventListener("submit", ev => {
        ev.preventDefault();
        onWelcomeFormSubmitted(form)
    });
}

function moveToDemo(userData) {
    setActiveFragment(FRAGMENT_DEMO);

    const original = DEMO.original;

    const container = document.getElementById("demo_canvas");
    const originalContainer = document.getElementById("demo_original");
    originalContainer.innerText = original;

    const onSelected = word => {
        if (match(word, original)) {
            console.log(word, original)
            moveToIntro(userData);
        }
    }

    setupTestCanvas(container, DEMO.words, onSelected);
}

function moveToIntro(userData) {
    setActiveFragment(FRAGMENT_INTRO);

    const useCamel = Math.random() > 0.5;
    userData.experiment = pickRandom(WORDS, NUM_TRIALS / 2);

    const button = document.getElementById("intro_start");
    button.addEventListener("click", _ => moveToExperiment(userData, useCamel, 0))
}

function moveToExperiment(userData, useCamel, trials) {
    if (trials === NUM_TRIALS) {
        moveToDone(userData);
        return;
    }

    setActiveFragment(FRAGMENT_EXPERIMENT);
    const data = userData.experiment[Math.floor(trials / 2)];
    const original = data.original;
    const words = trials % 2 === 0 && useCamel > 0 ? data.camel : data.kebab;

    const container = document.getElementById("experiment_canvas");
    const originalContainer = document.getElementById("experiment_original");
    originalContainer.innerText = original + " (" + trials + ")";

    let beginTime = 0;

    const onSelected = word => {
        const time = window.performance.now() - beginTime;
        if (match(original, word)) {
            userData.results.push(time);
            moveToExperiment(userData, !useCamel, trials + 1);
        }
    }

    setupTestCanvas(container, words, onSelected);
    beginTime = window.performance.now();
}

function moveToDone(userData) {
    setActiveFragment(FRAGMENT_DONE);

    const button = document.getElementById("done_download");
    button.addEventListener("click", _ => {
        userData.experiment = userData.experiment.map(it => it.original);

        const encoded = new TextEncoder().encode(JSON.stringify(userData));
        const blob = new Blob([encoded], {type: "application/octet-stream"});
        const url = window.URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.style.display = "none";
        document.body.appendChild(a);
        a.download = `results_${new Date().toISOString().slice(0, 10)}.json`;
        a.href = url;
        a.click();
        a.remove();
        setTimeout(() => window.URL.revokeObjectURL(url), 1000);
    });
}

function setActiveFragment(active) {
    const title = document.getElementById("title");
    Object.keys(this.fragments).forEach(key => {
        if (key === active) {
            title.innerHTML = this.fragments[key].title;
            this.fragments[key].element.className = "fragment_active";
        } else {
            this.fragments[key].element.className = "fragment_inactive";
        }
    });
}

/* *** Entry point *** */

function onLoad() {
    this.fragments = {
        [FRAGMENT_MAIN]: {
            title: "<i>CamelCase</i> vs. <i>kebab-case</i>",
            element: document.getElementById("fragment_main")
        },
        [FRAGMENT_WELCOME]: {
            title: "Please compile this form for to begin",
            element: document.getElementById("fragment_welcome")
        },
        [FRAGMENT_DEMO]: {
            title: "Let's give this a try!",
            element: document.getElementById("fragment_demo")
        },
        [FRAGMENT_INTRO]: {
            title: "Introduction",
            element: document.getElementById("fragment_intro")
        },
        [FRAGMENT_EXPERIMENT]: {
            title: "Experiment time!",
            element: document.getElementById("fragment_experiment")
        },
        [FRAGMENT_DONE]: {
            title: "Well done!",
            element: document.getElementById("fragment_done")
        },
    };

    moveToMain();
}
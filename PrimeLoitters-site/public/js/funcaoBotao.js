function primeiroQuadro() {
    document.getElementById("quadro2").style.display = "none";
    document.getElementById("quadro3").style.display = "none";

    document.getElementById("quadro1").style.display = "block";
    // document.getElementById("quadro1").innerHTML = quadro1();

    rankingMaquinas();
}

function segundoQuadro() {
    document.getElementById("quadro1").style.display = "none";
    document.getElementById("quadro3").style.display = "none";

    document.getElementById("quadro2").style.display = "block";
    //document.getElementById("quadro2").innerHTML = quadro2();
    grafico_alertas();
}

function terceiroQuadro() {
    document.getElementById("quadro1").style.display = "none";
    document.getElementById("quadro2").style.display = "none";

    document.getElementById("quadro3").style.display = "block";
    //document.getElementById("quadro3").innerHTML = quadro3();
}


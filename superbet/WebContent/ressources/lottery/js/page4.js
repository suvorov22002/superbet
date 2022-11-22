let page4 = $('#page4');
//let page4 = page4.jsp;

let page4content =
    "<div id='page4bloc1'>" +
    // shuffle
    "<div id='page4bloc1-shuffle' >" +
    "<img alt='shuffle' src='ressources/lottery/assets/shuffle.gif'/>" +
    "</div>" +

    // jackpot
    "<div id='page4bloc1-jackpot' >" +
    "<span class='label-bloc1'>jackpot</span>" +
    "<div class='jackpot-container-page-4'>" +
    "<span class='numbJackpot' id='divide4-jackpot'>" +
	"</span>" +
    "</div>" +
    "</div>" +

    "<div id='page4bloc1-bonus-last-tirage' >" +
    "<span>Dernier tirage</span>" +
    "<div id ='page4bloc1-bonus-last-tirage-contain'>" +
    "<div class='page4bloc1-bonus-last-tirage-item'>" +
    "<span class='label-race1' id='coderace_1'></span>" +
    "<span id='id_1'></span>" +
    "<span id='heure_1'></span>" +
    "<span class='' id='b_amount_1'></span>" +
    "</div>" +
 
    "<div class='page4bloc1-bonus-last-tirage-item'>" +
    "<span class='label-race1' id='coderace_2'></span>" +
    "<span id='id_2'></span>" +
    "<span id='heure_2'></span>" +
    "<span id='b_amount_2'></span>" +
    "</div>" +

    "<div class='page4bloc1-bonus-last-tirage-item'>" +
    "<span class='label-race1' id='coderace_3'></span>" +
    "<span id='id_3'></span>" +
    "<span id='heure_3'></span>" +
    "<span id='b_amount_3'></span>" +
    "</div>" +
    "</div>" +
    "</div>" +

    "<div id='page4bloc1-logo-time' >" +
    "<div id='page4bloc1-logo-time-logo' >" +
    "<img src='' id='myImage4' alt='logo'/>" +
    "</div>" +
    "<div id='page4bloc1-logo-time-time' >" +
    "<span>Time</span>" +
    "<span id='compteur2'></span>" +
    "</div>" +
    "</div>" +
    "</div>" +

    "<div id='page4bloc2'>" +
    "</div>" +

    "<div id='page4bloc3'>" +
    "<table class='page4bloc-table' id='page4bloc3-table-1'>" +
    "<tr>" +
     //  "<td colspan=6>total sum in the last 5 draw</td>" +
    "<td colspan=6>5 Numeros les plus tirees</td>" +
    "</tr>" +
    "</table>" +
 
    "<table class='page4bloc-table' id='page4bloc3-table-2'>" +
    "<tr>" +
    "<td colspan=12>Numero le plus tiree dans les 10 derniers tirages</td>" +
    "</tr>" +
    "</table>" +
    "</div>" +

    "<div id='page4bloc4'>" +
    "<table class='page4bloc-table' id='page4bloc4-table-1'>" +
    "<tr>" +
  //  "<td colspan=6>total sum in the last 5 draw</td>" +
   "<td colspan=6>5 Numeros les moins tirees</td>" +
    "</tr>" +
    "</table>" +

    "<table class='page4bloc-table' id='page4bloc4-table-2'>" +
    "<tr>" +
    "<td colspan=13>12 derniers multiplicateurs</td>" +
    "</tr>" +
    "</table>" +
    "</div>"
    ;

//fake array 5 derniers tirages
/*
let arrayDernierTirage = [
    {
        identifiant: '234567',
        heure: '15:45',
        arrayNumero: [1, 24, 38, 49, 50, 66, 75, 80, 79, 60, 11, 28, 16, 18, 51, 45, 17, 32, 27, 20],
        multiplicateur: '1x'
    },
    {
        identifiant: '234566',
        heure: '15:45',
        arrayNumero: [12, 24, 38, 49, 50, 66, 75, 80, 79, 60, 11, 28, 16, 18, 51, 45, 17, 32, 27, 20],
        multiplicateur: '1x'
    },
    {
        identifiant: '234565',
        heure: '14:45',
        arrayNumero: [35, 43, 31, 50, 68, 36, 72, 8, 80, 16, 24, 33, 56, 74, 46, 28, 78, 11, 45, 4],
        multiplicateur: '1x'
    },
    {
        identifiant: '234564',
        heure: '15:45',
        arrayNumero: [10, 24, 38, 49, 50, 66, 75, 80, 79, 60, 11, 28, 16, 18, 51, 45, 17, 32, 27, 20],
        multiplicateur: '1x'
    },
    {
        identifiant: '234563',
        heure: '15:45',
        arrayNumero: [78, 24, 38, 49, 50, 66, 75, 80, 79, 60, 11, 28, 16, 18, 51, 45, 17, 32, 27, 20],
        multiplicateur: '1x'
    },
];
*/
//fake array numero les plus tirees
let arrayNumeroLesPlusTirees_old = [
    {
        temp: '1x',
        numero: '30',
    },
    {
        temp: '2x',
        numero: '92',
    },
    {
        temp: '3x',
        numero: '62',
    },
    {
        temp: '4x',
        numero: '43',
    },
    {
        temp: '5x',
        numero: '35',
    },
    {
        temp: '6x',
        numero: '22',
    },
    {
        temp: '7x',
        numero: '28',
    },
    {
        temp: '8x',
        numero: '36',
    },
    {
        temp: '9x',
        numero: '12',
    },
    {
        temp: '10',
        numero: '43',
    },
];

//fake array pour les multiplicateurs
/*
let arrayDernierMultiplicateur = [
    {
        temp: arrayLastMultiplicateur[0]['multiplicateur'],
        time: arrayLastMultiplicateur[0]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[1]['multiplicateur'],
        time: arrayLastMultiplicateur[1]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[2]['multiplicateur'],
        time: arrayLastMultiplicateur[2]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[3]['multiplicateur'],
        time: arrayLastMultiplicateur[3]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[4]['multiplicateur'],
        time: arrayLastMultiplicateur[4]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[5]['multiplicateur'],
        time: arrayLastMultiplicateur[5]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[6]['multiplicateur'],
        time: arrayLastMultiplicateur[6]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[7]['multiplicateur'],
        time: arrayLastMultiplicateur[7]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[8]['multiplicateur'],
        time: arrayLastMultiplicateur[8]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[9]['multiplicateur'],
        time: arrayLastMultiplicateur[9]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[10]['multiplicateur'],
        time: arrayLastMultiplicateur[10]['heureTirage'].substring(11),
    },
    {
        temp: arrayLastMultiplicateur[11]['multiplicateur'],
        time: arrayLastMultiplicateur[11]['heureTirage'].substring(11),
    },
];
*/
//fake array pour la somme totale
//let arraySommeTotale5derniersTirage = [300, 100, 100, 300, 300];

//fake array first color of draw
//arrayFistColorOfDraw = [18, 34, 56, 78, 3];

function buildLastTirage() {
    let htmlTr = "<table id='page4bloc2-table'>";
    const page4bloc2 = $('#page4bloc2');

    
    for (let i = 0; i < arrayDernierTirage.length; i++) {
        htmlTr +=
            "<tr>" +
            "<td>" + arrayDernierTirage[i].identifiant + "</td>" +
            "<td>" + arrayDernierTirage[i].heure + "</td>";
        //start build number
        for (let j = 0; j < arrayDernierTirage[i].arrayNumero.length; j++) {
            htmlTr += "<td><span class='page4bloc2-table-number num" + arrayDernierTirage[i].arrayNumero[j] + " '>" + arrayDernierTirage[i].arrayNumero[j] + "</span></td>";
        }
        //end build number
        htmlTr += "<td>" + arrayDernierTirage[i].multiplicateur + "</td>" +
            "</tr>"
    }
    htmlTr += "</table>";
    page4bloc2.append(htmlTr);
}
 
function buildLastBonus(){
//    $("#coderace_1").empty();
//    $("#id_1").empty();
//    $("#heure_1").empty();
//    $("#b_amount_1").empty();
//    $("#coderace_2").empty();
//    $("#id_2").empty();
//    $("#heure_2").empty();
//    $("#b_amount_2").empty();
//    $("#coderace_3").empty();
//    $("#id_3").empty();
//    $("#heure_3").empty();
//    $("#b_amount_3").empty();
    
    var nbre = arrayLastBonus.length;
    switch(nbre){
        case 1:
            $("#coderace_1").prepend(arrayLastBonus[0].coderace);
            $("#id_1").prepend('ID: '+arrayLastBonus[0].code);
            $("#heure_1").prepend(arrayLastBonus[0].heure);
            $("#b_amount_1").prepend(arrayLastBonus[0].amount);
            break;
        case 2:
            $("#coderace_1").prepend(arrayLastBonus[0].coderace);
            $("#id_1").prepend('ID: '+arrayLastBonus[0].code);
            $("#heure_1").prepend(arrayLastBonus[0].heure);
            $("#b_amount_1").prepend(arrayLastBonus[0].amount);

            $("#coderace_2").prepend(arrayLastBonus[1].coderace);
            $("#id_2").prepend('ID: '+arrayLastBonus[1].code);
            $("#heure_2").prepend(arrayLastBonus[1].heure);
            $("#b_amount_2").prepend(arrayLastBonus[1].amount);
            break;
        case 3:
            $("#coderace_1").prepend(arrayLastBonus[0].coderace);
            $("#id_1").prepend('ID: '+arrayLastBonus[0].code);
            $("#heure_1").prepend(arrayLastBonus[0].heure);
            $("#b_amount_1").prepend(arrayLastBonus[0].amount);

            $("#coderace_2").prepend(arrayLastBonus[1].coderace);
            $("#id_2").prepend('ID: '+arrayLastBonus[1].code);
            $("#heure_2").prepend(arrayLastBonus[1].heure);
            $("#b_amount_2").prepend(arrayLastBonus[1].amount);

            $("#coderace_3").prepend(arrayLastBonus[2].coderace);
            $("#id_3").prepend('ID: '+arrayLastBonus[2].code);
            $("#heure_3").prepend(arrayLastBonus[2].heure);
            $("#b_amount_3").prepend(arrayLastBonus[2].amount);
            break;
        default:
            break;
    }
    
}

function buildNumeroLesPLusTirees() {
    //build first row
    let htmlTr = "<tr><td><span>temp</span></td>";
    const page4bloc3Table2 = $('#page4bloc3-table-2');
    for (let i = 0; i < arrayNumeroLesPlusTirees.length; i++) {
        htmlTr += "<td><span>" + arrayNumeroLesPlusTirees[i].temp + "</span></td>";
    }
    htmlTr += "</tr>";
    //end build first row

    //build second row
    htmlTr += "<tr><td><span>numero</span></td>";
    for (let i = 0; i < arrayNumeroLesPlusTirees.length; i++) {
        htmlTr += "<td><span>" + arrayNumeroLesPlusTirees[i].numero + "</span></td>";
    }
    //end build second row
    page4bloc3Table2.append(htmlTr);
}

function buildDernierMultiplicateur() {
   
    //build first row
    let htmlTr = "<tr><td><span>multi</span></td>";
    const page4bloc4Table2 = $('#page4bloc4-table-2');
//    for (let i = 0; i < arrayDernierMultiplicateur.length; i++) {
//        htmlTr += "<td><span>" + arrayDernierMultiplicateur[i].time + "</span></td>";
//    }
    for (let i = 0; i < arrayLastMultiplicateur.length; i++) {
        htmlTr += "<td><span>" + arrayLastMultiplicateur[i]['multiplicateur'] + "</span></td>";
    }
    htmlTr += "</tr>";
    //end build first row

    //build second row
    htmlTr += "<tr><td><span>time</span></td>";
//   for (let i = 0; i < arrayDernierMultiplicateur.length; i++) {
//        htmlTr += "<td><span>" + arrayDernierMultiplicateur[i].temp + "</span></td>";
//    }
    for (let i = 0; i < arrayLastMultiplicateur.length; i++) {
        htmlTr += "<td><span>" + arrayLastMultiplicateur[i]['heureTirage'].substring(11) + "</span></td>";
    }
    htmlTr += "</tr>";
    //end build second row
    page4bloc4Table2.append(htmlTr);
}

function buildSommeTotale() {
    //build first row
//    let htmlTr = "<tr><td><span>total</span></td>";
//   const page4bloc3Table1 = $('#page4bloc3-table-1');
 /*  
    for (let i = 0; i < arrayDernierTirage.length; i++) {
        var sum = 0;
        for (let j = 0; j < arrayDernierTirage[i].arrayNumero.length; j++) {
            sum = sum + parseInt(arrayDernierTirage[i].arrayNumero[j]);
        }
       
        arraySommeTotale5derniersTirage[i] = sum;
    }
    for (let i = 0; i < arraySommeTotale5derniersTirage.length; i++) {
        htmlTr += "<td>" + arraySommeTotale5derniersTirage[i] + "</td>";
    }

*/

    let htmlTr = "<tr><td><span>temp</span></td>";
    const page4bloc3Table1 = $('#page4bloc3-table-1');
    for (let i = 0; i < 5; i++) {
        htmlTr += "<td><span>" + arrayNumeroLesPlusTirees[i].temp + "</span></td>";
    }
    htmlTr += "</tr>";
    //end build first row

    //build second row
    htmlTr += "<tr><td><span>numero</span></td>";
    for (let i = 0; i < 5; i++) {
        htmlTr += "<td><span>" + arrayNumeroLesPlusTirees[i].numero + "</span></td>";
    }
    //end build second row
//    page4bloc3Table2.append(htmlTr);





 //   htmlTr += "</tr>";
    //end build first row
    page4bloc3Table1.append(htmlTr);
}

function buildFirstColorOfDraw() {
    //build first row
 //   let htmlTr = "<tr>";
 //   let htmlTr = "<tr><td><span>total</span></td>";
 //   const page4bloc4Table1 = $('#page4bloc4-table-1');
 /*   for (let i = 0; i < arrayFistColorOfDraw.length; i++) {
        htmlTr += "<td><span class='page4bloc2-table-number num" + arrayFistColorOfDraw[i] + "'>" + arrayFistColorOfDraw[i] + "</span></td>";
    }*/
 /*
    for (let i = 0; i < arrayDernierTirage.length; i++) {
        var sum = 0;
        for (let j = 0; j < arrayDernierTirage[i].arrayNumero.length; j++) {
            sum = sum + parseInt(arrayDernierTirage[i].arrayNumero[j]);
        }
        
        arraySommeTotale5derniersTirage[i] = sum;
    }
    for (let i = 0; i < arraySommeTotale5derniersTirage.length; i++) {
        htmlTr += "<td>" + arraySommeTotale5derniersTirage[i] + "</td>";
    }
*/

     let htmlTr = "<tr><td><span>temp</span></td>";
    const page4bloc4Table1 = $('#page4bloc4-table-1');
    for (let i = 0; i < 5; i++) {
        htmlTr += "<td><span>" + arrayNumeroLesMoinsTirees[i].temp + "</span></td>";
    }
    htmlTr += "</tr>";
    //end build first row

    //build second row
    htmlTr += "<tr><td><span>numero</span></td>";
    for (let i = 0; i < 5; i++) {
        htmlTr += "<td><span>" + arrayNumeroLesMoinsTirees[i].numero + "</span></td>";
    }

  //  htmlTr += "</tr>";
    //end build first row
    page4bloc4Table1.append(htmlTr);
}

let currentNumber2 = null;
 arrayJackpot2 = [
    3000.19,
    3000.25,
    3000.48,
    3000.18,
    3000.99,
    3001.60,
    3001.36,
    3001.46,
    3001.71,
    3001.97,
    3001.93,
    3002.99,
]

function animationJackpotCountNumberPartIntPage4(j, k) {
    let jackpotNumber = $(".numberJackpot-page-4").eq(k);
    let coordY = null;

    if (currentNumber2 == null) {
        coordY = -65 * j;
    } else {
        coordY = -65 * (j - currentNumber2);
        currentNumber2 = j
    }

    if (j == 0) {
        coordY = 0;
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");

    } else {
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");
    }
}

function animationJackpotCountNumberPartDecimalPage4(j, k) {
    let jackpotNumber = $(".numberJackpot-page-4-2").eq(k);
    let coordY = null;

    if (currentNumber2 == null) {
        coordY = -65 * j;
    } else {
        coordY = -65 * (j - currentNumber2);
        currentNumber2 = j
    }

    if (j == 0) {
        coordY = 0;
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");

    } else {
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");
    }
}

function setNumberInjackPotSecondPage4() {
    let i = 0;
    let setNumberInJackpotId = setInterval(
        () => {
            let item = arrayJackpot2[i].toString();
            //first number
            animationJackpotCountNumberPartIntPage4(parseInt(item[0]), 0);

            //second
            animationJackpotCountNumberPartIntPage4(parseInt(item[1]), 1);

            //third
            animationJackpotCountNumberPartIntPage4(parseInt(item[2]), 2);

            //fourth
            animationJackpotCountNumberPartIntPage4(parseInt(item[3]), 3);

            //decimal 1
            animationJackpotCountNumberPartDecimalPage4(parseInt(item[5]), 0);

            //decimal 2
            animationJackpotCountNumberPartDecimalPage4(parseInt(item[6]), 1);

            i++;
           // console.log(i, arrayJackpot2.length);
            if (i == arrayJackpot2.length) {
                clearInterval(setNumberInJackpotId);
            }
        }, 100
    )
}

function setNumberInjackPotFirstPage4() {
    let item = arrayJackpot2[0].toString();
    //first number
    animationJackpotCountNumberPartIntPage4(parseInt(item[0]), 0);

    //second
    animationJackpotCountNumberPartIntPage4(parseInt(item[1]), 1);

    //third
    animationJackpotCountNumberPartIntPage4(parseInt(item[2]), 2);

    //fourth
    animationJackpotCountNumberPartIntPage4(parseInt(item[3]), 3);

    //decimal 1
    animationJackpotCountNumberPartDecimalPage4(parseInt(item[5]), 0);

    //decimal 2
    animationJackpotCountNumberPartDecimalPage4(parseInt(item[6]), 1);

}

function animateShowElement() {
    //setNumberInjackPotFirstPage4();

    $('#page4bloc4-table-1').css("visibility", "visible");
    $('#page4bloc4-table-1').addClass('animated bounceInRight');

    setTimeout(
        () => {
            $('#page4bloc4-table-2').css("visibility", "visible");
            $('#page4bloc4-table-2').addClass('animated bounceInRight');

            setTimeout(
                () => {
                    $('#page4bloc3-table-1').css("visibility", "visible");
                    $('#page4bloc3-table-1').addClass('animated bounceInRight');

                    setTimeout(
                        () => {
                            $('#page4bloc3-table-2').css("visibility", "visible");
                            $('#page4bloc3-table-2').addClass('animated bounceInRight');

                            setTimeout(
                                () => {
                                    $('#page4bloc2').css("visibility", "visible");
                                    $('#page4bloc2').addClass('animated bounceInRight');

                                    setTimeout(
                                        () => {
                                            $('#page4bloc1-jackpot').css("visibility", "visible");
                                            $('#page4bloc1-jackpot').addClass('animated bounceInRight');
                                            setTimeout(
                                                () => {
                                                    setNumberInjackPotSecondPage4();
                                                }, 2500
                                            )

                                            setTimeout(
                                                () => {
                                                    $('#page4bloc1-bonus-last-tirage').css("visibility", "visible");
                                                    $('#page4bloc1-bonus-last-tirage').addClass('animated bounceInRight');

                                                    setTimeout(
                                                        () => {
                                                            $('#page4bloc1-logo-time-logo').css("visibility", "visible");
                                                            $('#page4bloc1-logo-time-logo').addClass('animated bounceInRight');

                                                            $('#page4bloc1-shuffle').css("visibility", "visible");
                                                            $('#page4bloc1-shuffle').addClass('animated bounceInRight');


                                                            setTimeout(
                                                                () => {
                                                                    $('#page4bloc1-logo-time-time').css("visibility", "visible");
                                                                    $('#page4bloc1-logo-time-time').addClass('animated bounceInRight');

                                                                    setTimeout(
                                                                        () => {
                                                                            animateTableLastTirage();
                                                                        }, 100
                                                                    )
                                                                }, 100)

                                                        }, 100)

                                                }, 100)

                                        }, 100)

                                }, 300)

                        }, 300)

                }, 100)

        }, 100)

}

function animateTableLastTirage() {
    let i = 0;
    let intervalId = setInterval(
        () => {
            $('#page4bloc2 tr').eq(i).addClass('animated pulse');
            i++;
            if (i > $('#page4bloc2 tr').length) {

                clearInterval(intervalId);
                zoomTableOther();
            }
        }, 1800
    );

    arrayInterval.push(intervalId);

}

function zoomTableOther() {
    $('#page4bloc3-table-1').removeClass('bounceInRight');
    $('#page4bloc3-table-1').addClass('pulse');

    setTimeout(
        () => {
            $('#page4bloc3-table-2').removeClass('bounceInRight');
            $('#page4bloc3-table-2').addClass('pulse');

            setTimeout(
                () => {
                    $('#page4bloc4-table-1').removeClass('bounceInRight');
                    $('#page4bloc4-table-1').addClass('pulse');

                    setTimeout(
                        () => {
                            $('#page4bloc4-table-2').removeClass('bounceInRight');
                            $('#page4bloc4-table-2').addClass('pulse');

                            setTimeout(
                                () => {
                                    hideAllTable();
                                }, 1500
                            )
                        }, 1800
                    )
                }, 1800
            )
        }, 1800
    )
}

function hideAllTable() {
    $('#page4bloc4-table-1').removeClass('bounceInRight')
    $('#page4bloc4-table-1').addClass('bounceOutRight')

    setTimeout(
        () => {
            $('#page4bloc4-table-2').removeClass('bounceInRight')
            $('#page4bloc4-table-2').addClass('bounceOutRight')

            setTimeout(
                () => {
                    $('#page4bloc3-table-1').removeClass('bounceInRight')
                    $('#page4bloc3-table-1').addClass('bounceOutRight')

                    setTimeout(
                        () => {
                            $('#page4bloc3-table-2').removeClass('bounceInRight')
                            $('#page4bloc3-table-2').addClass('bounceOutRight')

                            setTimeout(
                                () => {
                                    $('#page4bloc2').removeClass('bounceInRight')
                                    $('#page4bloc2').addClass('bounceOutRight')

                                    setTimeout(
                                        () => {
                                            $('#page4bloc1-jackpot').removeClass('bounceInRight')
                                            $('#page4bloc1-jackpot').addClass('bounceOutRight')

                                            setTimeout(
                                                () => {
                                                    $('#page4bloc1-bonus-last-tirage').removeClass('bounceInRight')
                                                    $('#page4bloc1-bonus-last-tirage').addClass('bounceOutRight')

                                                    setTimeout(
                                                        () => {
                                                            $('#page4bloc1-logo-time-logo').removeClass('bounceInRight')
                                                            $('#page4bloc1-logo-time-logo').addClass('bounceOutRight')

                                                            setTimeout(
                                                                () => {
                                                                    $('#page4bloc1-logo-time-time').removeClass('bounceInRight')
                                                                    $('#page4bloc1-logo-time-time').addClass('bounceOutRight')

                                                                    $('#page4bloc1-shuffle').removeClass('bounceInRight')
                                                                    $('#page4bloc1-shuffle').addClass('bounceOutRight')
                                                                }, 100)
                                                        }, 100)
                                                }, 100)
                                        }, 100)
                                }, 100)
                        }, 100)
                }, 100)
        }, 100)
}

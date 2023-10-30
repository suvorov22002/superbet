let page3 = $('#page3');
//let page3 = page3.jsp;
let page3content =
	
    "<div class='modal face' id='myBonus' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'>" +
    "<div class='modal-dialog' role='document'>" +
    "<div class='modal-content'>" +
    "<div class='modal-header'>" +      
    "<h4 class='modal-title' id='myModalLabel'>JACKPOT</h4>" +       
    "</div>" +      
    "<div class='modal-body'>" +    
    "<span class='_pbonus'>Code Ticket</span><br/><span id='codek_bonus' class='pbonus'></span> <br/><span id='amountk_bonus' class='mbonus'> FCFA</span><br/>" +            
    "<span id='coderacek_bonus' class='pbonus'>Bravooooo!!!</span>"    +            
    "</div>" +  
    "</div>" +
    "</div>" +
    "</div>" +

    "<div class='modal face' id='myCagnot' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'>" +
    "<div class='modal-dialog' role='document'>" +
    "<div class='modal-content'>" +
    "<div class='modal-header'>" +      
    "<h4 class='modal-title' id='myModalLabel'>CAGNOTTE</h4>" +       
    "</div>" +      
    "<div class='modal-body'>" +    
    "<span class='_pbonus'>Code Ticket</span><br/><span id='codek_cagnot' class='pbonus'></span> <br/><span id='lot_cagnot' class='mbonus'></span><br/>" +            
    "<span id='coderacek_cagnot' class='pbonus'>Bravooooo!!!</span>"    +            
    "</div>" +  
    "</div>" +
    "</div>" +
    "</div>" +    

    "<div id ='page3-output-winnner-container'>" +
    "<div id='page3-output-winnner-1'>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "</div>" +
    "<div id='page3-output-winnner-2'>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "<div class='number-output-item zoom'>" +
    "</div>" +
    "</div>" +
    "</div>" +


    "<div id='page3-logo'>" +
    "<div class='logo-container'><img src='' id='myImage3' alt='logo'/></div>" +
    "</div>" +
    "<div id='page3-jackpot-tirage'>" +
    "<div id='page3-jackpot'>" +
    "<span class='label label-bloc1'>jackpot</span>" +
    "<div class='jackpot-container-page-3'>" +
    "<span class='divide3-jackpotPage3' id='divide3-jackpot'>" +
	"</span>" +
    "</div>" +
    "</div>" +
    "<div id='page3-tirage'>" +
    "<div class='page3-tirage-item'>" +
    "<span class='label-bloc1'>tirage</span>" +
    "<span class='value' id='drawnumb3'></span>" +
    "</div>" +

    "<div class='page3-tirage-item'>" +
    "<span class='label-bloc1'>M</span>" +
    "<span id='multi' class='value'>-</span>" +
    "</div>" +
    "</div>" +
    "</div>";

//let outputNumber = [12, 24, 38, 49, 50, 66, 75, 80, 79, 60, 11, 28, 16, 18, 51, 45, 17, 32, 27, 20]

function setOutputNumber() {
    
    //set number in first row
    let page3OutputWinnner1 = $('#page3-output-winnner-1');
    let children1 = page3OutputWinnner1.children()
    for (let i = 0; i < children1.length; i++) {
        let currentChildren1 = children1.eq(i);
        currentChildren1.append("<span class='numOutput num" + outputNumber[i] + "'>" + outputNumber[i] + "</span>");
    }

    //set number in second row
    let page3OutputWinnner2 = $('#page3-output-winnner-2');
    let children2 = page3OutputWinnner2.children()
    for (let i = 0; i < children2.length; i++) {
        let currentChildren2 = children2.eq(i);
        currentChildren2.append("<span class='numOutput num" + outputNumber[i + 10] + "'>" + outputNumber[i + 10] + "</span>");
    }
    updateColor();
    
}

//2
function animationJackPot() {
    setNumberInjackPotFirstPage3();
    $('#page3-jackpot-tirage').css('visibility', 'visible');
    $('#page3-jackpot-tirage').children().addClass('animated bounceInUp');
    
    let currentMulti = $('#page3 #multi');
    
    setTimeout(
        () => {
            setNumberInjackPotSecondPage3();
            currentMulti.text(multiplicateur+'x');
        }, 2500
    )

    setTimeout(
        () => {
            animationLogo();
        }, 2000
    )
   
}

//3
function animationLogo() {
    $('#page3-logo').css('visibility', 'visible');
    $('#page3-logo').children().addClass('animated fadeIn');
    setTimeout(
        () => {
            zoomAnimationOutPutNumberWithBorder();
        }, 2000
    )
}

//1
function animationStartOutPutNumber() {
    $('#page3-output-winnner-1').css('visibility', 'visible');
    $('#page3-output-winnner-2').css('visibility', 'visible');
    // $('#page3-output-winnner-1').children().addClass('animated pulse');
    // $('#page3-output-winnner-2').children().addClass('animated pulse');
  
    setTimeout(
        () => {
              buscarBonus();
            animationJackPot();

        }, 2000
    )
}

//4
function zoomAnimationOutPutNumberWithBorder() {
    let i = 0;
    let intervalId = setInterval(
        () => {
            $('#page3-output-winnner-1').children().eq(i).addClass('animated pulse');
            i++;
            // remove border

            if (i > $('#page3-output-winnner-1').children().length) {
                clearInterval(intervalId);
                let j = 0;
                let intervalId2 = setInterval(
                    () => {
                        $('#page3-output-winnner-2').children().eq(j).addClass('animated pulse');
                        j++;
                        // remove border

                        if (j > $('#page3-output-winnner-2').children().length) {
                            clearInterval(intervalId2);

                            animationHideJackpotLogo();
                        }
                    }, 50
                )
            }
        }, 100
    );
    arrayInterval.push(intervalId);

}


let currentNumber4 = null;
let arrayJackpot4 = [
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

function animationJackpotCountNumberPartIntPage3(j, k) {
    let jackpotNumber = $(".numberJackpot-page-3").eq(k);
    let coordY = null;

    if (currentNumber4 == null) {
        coordY = -65 * j;
    } else {
        coordY = -65 * (j - currentNumber4);
        currentNumber4 = j
    }

    if (j == 0) {
        coordY = 0;
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");

    } else {
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");
    }
}

function animationJackpotCountNumberPartDecimalPage3(j, k) {
    let jackpotNumber = $(".numberJackpot-page-3-2").eq(k);
    let coordY = null;

    if (currentNumber4 == null) {
        coordY = -65 * j;
    } else {
        coordY = -65 * (j - currentNumber4);
        currentNumber4 = j
    }

    if (j == 0) {
        coordY = 0;
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");

    } else {
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");
    }
}

function setNumberInjackPotSecondPage3() {
    let i = 0;
    let setNumberInJackpotId = setInterval(
        () => {
            let item = arrayJackpot4[i].toString();
            //first number
            animationJackpotCountNumberPartIntPage3(parseInt(item[0]), 0);

            //second
            animationJackpotCountNumberPartIntPage3(parseInt(item[1]), 1);

            //third
            animationJackpotCountNumberPartIntPage3(parseInt(item[2]), 2);

            //fourth
            animationJackpotCountNumberPartIntPage3(parseInt(item[3]), 3);

            //decimal 1
            animationJackpotCountNumberPartDecimalPage3(parseInt(item[5]), 0);

            //decimal 2
            animationJackpotCountNumberPartDecimalPage3(parseInt(item[6]), 1);

            i++;
            //console.log(i, arrayJackpot4.length);
            if (i == arrayJackpot4.length) {
                clearInterval(setNumberInJackpotId);
            }
        }, 100
    )
}

function setNumberInjackPotFirstPage3() {
    let item = arrayJackpot4[0].toString();
    //first number
    animationJackpotCountNumberPartIntPage3(parseInt(item[0]), 0);

    //second
    animationJackpotCountNumberPartIntPage3(parseInt(item[1]), 1);

    //third
    animationJackpotCountNumberPartIntPage3(parseInt(item[2]), 2);

    //fourth
    animationJackpotCountNumberPartIntPage3(parseInt(item[3]), 3);

    //decimal 1
    animationJackpotCountNumberPartDecimalPage3(parseInt(item[5]), 0);

    //decimal 2
    animationJackpotCountNumberPartDecimalPage3(parseInt(item[6]), 1);

}

function animationHideJackpotLogo() {
    setTimeout(
        () => {
            $('#page3-logo').children().removeClass('animated fadeIn');
            $('#page3-logo').children().addClass('animated bounceOutDown');

            $('#page3-jackpot-tirage').children().removeClass('animated bounceInUp');
            $('#page3-jackpot-tirage').children().addClass('animated bounceOutDown');

            setTimeout(
                () => {
                animationHideNumeroOutput();
                }, 300
            )
        }, 2500
    )
}

function animationHideNumeroOutput() {
    let i = 10;
    let j = 10;

    let intervalId1 = setInterval(
        () => {
            $('#page3-output-winnner-1').children().eq(i).removeClass('animated pulse');
            $('#page3-output-winnner-1').children().eq(i).addClass('animated bounceOutDown');
            i--;
            if (i === 0) {
                //startDraw(1);
                clearInterval(intervalId1);
                window.location.reload();
            }
        }, 100
    )
    arrayInterval.push(intervalId1);

    let intervalId2 = setInterval(
        () => {
            $('#page3-output-winnner-2').children().eq(i).removeClass('animated pulse');
            $('#page3-output-winnner-2').children().eq(i).addClass('animated bounceOutDown');
            j--;
            if (j < 0) {
                clearInterval(intervalId2);
            }
        }, 100
    )
    arrayInterval.push(intervalId2);

}

function buscarBonus(){
  console.log("Search Bonus");

    $.ajax({
            url:"managebonusk",
            type:"GET",
            async: false,
            data:{
                            'coderace':coderace
                        },
            success:function(result){
                // Pour chaque résultat du tableau
                $.each(result, function(index, value){
                   var bonus = value.bonusk;
                   var barcod = value.barcode;

                   $("#codek_cagnot").empty(); 
                   $("#amountk_cagnot").empty();
                   $("#coderacek_cagnot").empty();

                   $("#codek_bonus").empty(); 
                   $("#amountk_bonus").empty();
                   $("#coderacek_bonus").empty();

                   console.log('WINNER BONUS: '+bonus);
                   if(bonus == 1 && barcod != 0){
                     
                     $("#codek_bonus").prepend(value.codebonus); 
                     $("#amountk_bonus").prepend(value.montantbonus); 
                     $("#coderacek_bonus").prepend(value.partner+' Bravooooo!!!'); 
                     $('#myBonus').modal('show');

                     setTimeout(function(){
                         $('#myBonus').modal('hide');
                        // $('#page3-output-winnner-container').css('display','block');
                         $("#codek_cagnot").prepend(value.mise); 
                         $("#amountk_cagnot").prepend(value.barcode); 
                         $("#coderacek_cagnot").prepend(value.partner+' Bravooooo!!!'); 
                         $('#myCagnot').modal('show');
                         setTimeout(function(){
                             $('#myCagnot').modal('hide');
                            // $('#page3-output-winnner-container').css('display','block');
                         }, 10000);

                     }, 10000);
                   }
                   else if(bonus == 1){
                  //   $('#page3-output-winnner-container').css('display','none');
                 //  console.log('WBONUS: '+value.codebonus+'\n'+value.montantbonus+'\n'+value.partner);
                     $("#codek_bonus").prepend(value.codebonus); 
                     $("#amountk_bonus").prepend(value.montantbonus); 
                     $("#coderacek_bonus").prepend(value.partner+' Bravooooo!!!'); 
                     $('#myBonus').modal('show');
                     setTimeout(function(){
                         $('#myBonus').modal('hide');
                        // $('#page3-output-winnner-container').css('display','block');

                     }, 10000);
                   }
                   else if(barcod != 0){
                         $("#codek_cagnot").prepend(value.mise); 
                         $("#amountk_cagnot").prepend(value.barcode); 
                         $("#coderacek_cagnot").prepend(value.partner+' Bravooooo!!!'); 
                         $('#myCagnot').modal('show');
                         setTimeout(function(){
                             $('#myCagnot').modal('hide');
                            // $('#page3-output-winnner-container').css('display','block');
                         }, 10000);
                   }

                });
            }
        });   
}

function buscarCagnot(){
  console.log("Search Cagnot");

    $.ajax({
            url:"managecagnot",
            type:"GET",
            async: false,
            data:{
                            'coderace':coderace
                        },
            success:function(result){
                // Pour chaque résultat du tableau
                $.each(result, function(index, value){
                   var bonus = value.barcode;

                   $("#codek_cagnot").empty(); 
                   $("#amountk_cagnot").empty();
                   $("#coderacek_cagnot").empty();

                   console.log('WINNER CAGNOTTE: '+bonus);
                   if(bonus == 1){
                  //   $('#page3-output-winnner-container').css('display','none');
                 //  console.log('WBONUS: '+value.codebonus+'\n'+value.montantbonus+'\n'+value.partner);
                     $("#codek_cagnot").prepend(value.mise); 
                     $("#amountk_cagnot").prepend(value.barcode); 
                     $("#coderacek_cagnot").prepend(value.partner+' Bravooooo!!!'); 
                     $('#myCagnot').modal('show');
                     setTimeout(function(){
                         $('#myCagnot').modal('hide');
                        // $('#page3-output-winnner-container').css('display','block');
                     }, 10000);
                   }
                });
            }
        });   
}

async function _finishDraw(){

    var url = urlServeur+'/finish-draw/'+coderace;
    const response = await fetch(url,{
        method: 'POST',
        body: {},
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const myJson = await response.json();
    console.log('endraw: '+myJson);

            //var itoken = localStorage.getItem("token");
/*
            $.ajax({
                url:"finishdraw",
                type:"POST",
                async: false,
                data:{
                    'partner':coderace
                },
                success:function(result){
                    $.each(result, function(index, value){
                       
                    }
            );
            
        }

        });
    */
}

 async function finishDraw(){
   
   var url = urlServeur+'/finish-draw/'+coderace;
    console.log("url "+url);
       
      
        const response1 = await fetch(url);
        const myResp = await response1.json(); //extract JSON from the http response
        
        console.log("gameState myResp"+myResp);
    }



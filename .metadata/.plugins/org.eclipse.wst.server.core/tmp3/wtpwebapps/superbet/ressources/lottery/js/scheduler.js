let arrayInterval = [];
let timekeno;
let time;
let  _str_combi = [];
let num_tirage;
let pg3 = false;
let draw = "";
let draw_boules = [];
let pg1 = false;
let pg2 = false;
let imagePage1;
 
async function buildPage4() {
  //  console.log('ICI PAGE2');
    await page1.hide();

    await page2.hide();
    await page3.hide();

    await page4.empty();
    await page4.append(page4content);

    var imag = 'ressources/lottery/assets/logo_'+coderace+'.jpeg'
    $("#myImage4").attr("src", imag);

    
    document.getElementById("myImage4").src = imag;

    buildFirstColorOfDraw();
    buildSommeTotale();
    buildDernierMultiplicateur();
    buildNumeroLesPLusTirees();
    buildLastTirage();
    updateColor();
    buildLastBonus();
    await page4.show();
    animateShowElement();
}

async function buildPage4_1() {
  //  console.log('ICI PAGE2');
    await page1.hide();
    await page2.hide();
    await page3.hide();
   
  

//    var imag = 'ressources/lottery/assets/logo_'+coderace+'.jpeg'
//    $("#myImage4").attr("src", imag);

   
//    document.getElementById("myImage4").src = imag;

    buildFirstColorOfDraw();
    buildSommeTotale();
    buildDernierMultiplicateur();
    buildNumeroLesPLusTirees();
    buildLastTirage();
    updateColor();
    buildLastBonus();
    await page4.show();
    animateShowElement();
}

async function buildPage1() {
    await page2.hide();
    await page3.hide();
    await page4.hide();
    await page1.empty();
    await page1.append(page1content);

    var imag = 'ressources/lottery/assets/logo_'+coderace+'.jpeg'
    $("#myImage1").attr("src", imag);
    document.getElementById("myImage1").src = imag;

    /* logo cagnotte */
    console.log('imagePage1: '+imagePage1);
    if (imagePage1 === ''){
        $("#cagnotte-container").css('display','none');
        var sp_span = document.getElementById("label-cagnotte");
        sp_span.innerHTML = "";
    }
    else{
        var image1 = 'ressources/lottery/assets/'+imagePage1+'.jpg'
                $("#myImage").attr("src", image1);
        document.getElementById("myImage").src = image1;
    }
    

    

    await page1.show();
    animationShowBounceTable();
}

async function buildPage1_1() {
    await page2.hide();
    await page3.hide();
    await page4.hide();
//    var imag = 'ressources/lottery/assets/logo_'+coderace+'.jpeg'
//    $("#myImage1").attr("src", imag);
//    document.getElementById("myImage1").src = imag;
    await page1.show();
    animationShowBounceTable();
}

async function buildPage3() {
    await page2.hide();
    await page1.hide();
    await page4.hide();
    await page1.empty();
    await page4.empty();
    await page2.empty();
    await page3.empty();
    await page3.append(page3content);
    str_combi = "";
    updateDrawNum2();
    var imag = 'ressources/lottery/assets/logo_'+coderace+'.jpeg'
    $("#myImage3").attr("src", imag);

    document.getElementById("myImage3").src = imag;
     console.log("num_tirage page 3:"+numero_tirage);
    
    
  //   $("#drawnumb3").text(numero_tirage);
    setOutputNumber();
    await page3.show('slow');
   
    animationStartOutPutNumber();
}

async function buildPage2() {

    await page1.hide();
    await page3.hide();
    await page4.hide();

    await page1.empty();
    await page3.empty();
    await page4.empty();
    await page2.empty();

    await page2.append(page2content);
    await page2.show();

    
    updateDrawNum();
    
  // console.log("draw_boules: "+draw_boules.length);
    let buscarInterval = setInterval(async function(){
        if(draw_boules.length < 20){
              console.log("draw_boules: "+draw_boules.length);
            buscarDraw();
           
        }
        else{
              console.log("draw_boules2: "+draw_boules.length);
            startDraw(2);
            clearInterval(buscarInterval);
             setarrayNumroSortant();
            animationCircle();
        }
       
    },1000);

   // }
    //addNewDraw("update");
 //  $("#drawnumb").text(numero_tirage);
                
    
}

function updateColor() {
    //start build color from number
    for (let k = 1; k <= 80; k++) {
        let selector = 'span.num' + k;
        if (k >= 1 && k <= 20 && $(selector).length > 0) {
            gsap.to(selector, { background: 'radial-gradient(circle at 5px 5px,#1a7332, green)' });
        }
        if (k >= 21 && k <= 40 && $(selector).length > 0) {
            gsap.to(selector, { background: 'radial-gradient(circle at 5px 5px,#336dff, blue)' });
        }
        if (k >= 41 && k <= 60 && $(selector).length > 0) {
            gsap.to(selector, { background: 'radial-gradient(circle at 5px 5px,#ad2b2b, red)' });
        }
        if (k >= 61 && k <= 80 && $(selector).length > 0) {
            gsap.to(selector, { background: 'radial-gradient(circle at 5px 5px,#f7d71e, #ebb746)' });
        }
    }
    //end build color from number
}

function timeToLeft(timestarted) {
    //time to left 
    let timeStart = 28;
    time = timestarted;
    pg1 = false;
    pg2 = false;
   
    let timePage1 = [136, 86, 36];
    let timePage2 = [158, 108, 58, 8];
    let intervalIdTimeLeft = setInterval(
        () => {
          //  if(gamestate == 1)  time--;
           
            //console.log("TIMEKEO sheduler: "+timekeno+" - "+gamestate);
            if(timekeno == 185 && gamestate == undefined){
                $('#compteur1').text('--');
            }
            else{
                $('#compteur1').text(timekeno);
            }
            
            $('#compteur2').text(timekeno);


            if (timekeno < 2) {
                clearInterval(intervalIdTimeLeft);
                buildPage2();
                startDraw(2);
            }

            if (timekeno < 5) {
                canSubmit = false;
            }

            if (gamestate==1 && ( (158<timekeno && timekeno<186) || (timekeno < 137 && timekeno > 108) || (timekeno < 87 && timekeno > 58) || (timekeno < 37 && timekeno > 8))&& pg1 == false) {
                    clearIntervalId();
                    buildPage1();
                    pg1 = true;
                    pg2 = false;
            }

            if (gamestate==1 && ((timekeno < 159 && timekeno > 136) || (timekeno < 109 && timekeno > 86) || (timekeno < 59 && timekeno > 36) || (timekeno < 9 && timekeno > 1))&& pg2 == false) {
                    buildPage4();
                    pg1 = false;
                    pg2 = true;
            }
/*
            for (let i = 0; i < timePage1.length; i++) {
                if (timePage1[i] >= timekeno && pg1 == false) {
                    buildPage1();
                    pg2 = true;
                    pg1 = false;
                }
            }

            for (let i = 0; i < timePage2.length; i++) {
                if (timePage2[i] == timekeno) {
                    buildPage4();
                }

            }
*/
            //gestion de la connexion du client lorsque le tirage est dej lancé
        //    if(gamestate != 1 && gamestate != 4 && timekeno == 185 && gamestate != 0){
            if((gamestate == 2 || gamestate == 3) && timekeno == 185 && str_combi.length != 0) {
                console.log("_str_combi_str_combi: "+str_combi.length);
                 if(str_combi != "" && str_combi != undefined){
                      _str_combi = str_combi.split("-");
                      console.log("_str_combi: "+_str_combi);
                 }
               console.log("_str_combi.length: "+_str_combi.length);
                if(_str_combi.length < 20){
                    clearInterval(intervalIdTimeLeft);
                    buildPage2();
                }
                else if(_str_combi.length == 20 && pg3 == false){
                    buildPage1();
                    pg3 = true;
                }
            }
            else if(str_combi.length == 0){

            }

            if(gamestate == 0 && pg3 == false){
                 clearIntervalId();
                 buildPage1();
            }

        }, 1000
    )
    //buscarDraw();
}

function init(times) {
    //let time = times;
    //userAction();
    clearIntervalId();
    startDraw(1);
    seekCagnot();
    recordDisplay(); // recuperation du temps, combinaison, gamestate

    console.log('GAME STATE: '+gamestate);
    console.log('COMBI: '+str_combi);
    console.log('recordDisplay '+timekeno);

    let retour = $("#errors").text();

    if(timekeno == undefined){
        console.log("compteur non init: "+timekeno);
    }
    
    timeToLeft(timekeno);
    //callXHR();
}

function clearIntervalId() {
    for (let i = 0; i < arrayInterval.length; i++) {
        clearInterval(arrayInterval[i]);
    }
}

function callXHR(){
    var xhr = null;
    if(window.XMLHttpRequest || window.ActiveXObject){
        if(window.ActiveXObject){
            try{
                xhr = new ActiveXObject("Msxml2.XMLHTTP");
                console.log('HERE XMLHTTPRequest');
            }catch(e){
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
        }else{
            xhr = new XMLHttpRequest();
            console.log('XMLHTTPRequest');
        }
    }else{
        alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
        return;
    }
}

function recordDisplay(){
    $.ajax({
        url:"updatebonus",
        type:"GET",
        async: false,
         data:{
                'coderace':coderace
            },
        success:function(result){
            $.each(result, function(index, value){
              //  timekeno = value.timekeno;
                str_combi = value.combi;
              //  gamestate = value.gamestate;


            });
        }
      });
}

function startDraw(state){
     console.log("start-draw");
    $.ajax({
        url:"getState",
        type:"GET",
        async: false,
         data:{
                'coderace':coderace,
                'state':state
            },
        success:function(result){
            $.each(result, function(index, value){
              //  timekeno = value.timekeno;
            
                //gamestate = value.gamestate;


            });
        }
      });
}

function seekCagnot(){
     console.log("seek-cagnotte");
    $.ajax({
        url:"getCagnotte",
        type:"GET",
        async: false,
         data:{
                'coderace':coderace
            },
        success:function(result){
            $.each(result, function(index, value){
                var barc = result['barcode'];
                console.log("CAGNOTTE: "+result['jeu']);
                console.log("CAGNOTTE: "+result['lot']);

                if (barc != 0) {
                    imagePage1 = "";
                }
                else{
                    imagePage1 = result['lot'];
                }   

            });
        }
      });
}

function __startDraw(){


    let interStart = setInterval(async () => {
        console.log("start-draw");
                if(urlServeur != undefined){
                    var url = urlServeur+'/start-draw/'+coderace;
                    const response = await fetch(url);
                //    const myJson = await response.json(); //extract JSON from the http response
                    clearInterval(interStart);
                }
                
            },800);

  
        
        
}

function buscarDraw(){
    console.log("Generate draw");
    trouve_draw = false;
    $.ajax({
            url:"generatedraw",
            type:"GET",
            async: false,
            data:{
                'coderace':coderace
            },
            success:function(result){
                // Pour chaque résultat du tableau
                $.each(result, function(index, value){
                 
                    // on retrouve les paramètres qu'on avait fixé via l'API Json dans la servlet
                   draw = value.drawnumbk;
                   console.log("drawnumbk: "+draw);
                   draw_boules = [];

                   draw_boules = draw.split("-");
                  // arrayNumroSortant = draw_boules;
              
                     trouve_draw = true;
                  for(let j=0;j<20;j++){
                     //arrayNumroSortant.pop();
                     arrayNumroSortant.push(draw_boules[j]);
                  }
                   outputNumber = arrayNumroSortant;
                 
                   multiplicateur = value.multi;
                

            //    numero_tirage =  value.drawnumk;
            //    console.log("num_tirage:"+numero_tirage);
            //    $("#drawnumb").text(num_tirage);
            //    $("#drawnumb3").text(num_tirage);

                });
            }
        });   
}

//recupératio de l'objet keno en cours
 async function buscarDraw_old(){
    console.log('myJson buscarDraw '+coderace);
    var url = 'http://localhost:9090/api/v1/supergames/drawcombi/'+coderace;
    var draw = null;
    

    const response1 = await fetch(url);
    const myJson2 = await response1.json(); //extract JSON from the http response
      // do something with myJson
    
    setTimeout(
        () => {
            // page2
          //  $("#drawnumb").empty();
            // page3
          //  $("#drawnumb3").empty();
        }, 2000
    )
       draw = myJson2['drawnumbK'];

       var draw_boules = [];

       draw_boules = draw.split("-");
       multiplicateur = myJson2['multiplicateur'];
      // arrayNumroSortant = draw_boules;
     
     for(let j=0;j<20;j++){
         arrayNumroSortant.push(draw_boules[j]);
     }
     trouve_draw = true;
     outputNumber = arrayNumroSortant;
    
    num_tirage =  myJson2['drawnumK'];
    console.log("num_tirage:"+num_tirage);
    $("#drawnumb").text(num_tirage);
    $("#drawnumb3").text(num_tirage);
}




init(185);




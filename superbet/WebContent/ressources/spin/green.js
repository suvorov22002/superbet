//var numberWinner = Math.floor(Math.random() * 36);
var numberWinner;
var finish;
let black = [2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35];
let red = [1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36];

var wheel = $(".wheel"),
    active = $(".active"),
    numberOutput = $("#numberOutput"),
    currentRotation,
    lastRotation,
    tolerance;

numberOutput.html('?');
numberOutput.css('border-radius','50%');

var $btnPlay = $("#btnPlay"),
    $btnSlowMo = $("#btnStop");

// wheel
var getter = gsap.getProperty(wheel[0]);
InertiaPlugin.track(wheel[0], "rotation");

var spinWheel = gsap.timeline({ paused: true });

spinWheel
    .to(wheel, {
        rotation: 360,
        repeat: -1,
        ease: "none",
        transformOrigin: "50% 50%",
    })

function startSpin() {
    numberOutput.html('?');
    spinWheel.play().timeScale(0)
    gsap.to(spinWheel, { timeScale: 1, duration: 2, ease: "power3.in" });
    //recuperation du numéro tiré
    buscarDraw();

    setTimeout(() => {
        stopSpin();
    }, 10000)
}

function stopSpin() {
    gsap.to(wheel, {
        scale: 2.5,
        duration: 1,
        top: '40vw',
        left: '5vw',
        ease: "power3.in"
    });

    gsap.to(active, {
        scale: 2.5,
        duration: 1,
        top: '12.5vw',
        left: '24.6vw',
        ease: "power3.in"
    });

    gsap.to(numberOutput, {
        top: '60vw',
        left: '24vw',
        scale: 2,
        duration: 1,
        ease: "power3.in",
        onComplete: () => {
            updateColor(numberWinner);
            numberOutput.html(numberWinner);
        },
    });


    let angleIndex = segment.findIndex(function (item) {
        return item.segmentName == numberWinner;
    });

    var stopAt = segment[angleIndex].angle + 1440 - 33;

    spinWheel.pause();

    gsap.to(wheel, {
        inertia: {
            rotation: {
                end: stopAt
            }
        },
        onComplete: () => {

            gsap.to(numberOutput, {
                scale: 1,
                duration: 1,
                top: '20.9vw',
                left:'17vw',
                ease: "power3.in",
                delay: 3,
            });

            gsap.to(active, {
                scale: 1,
                ease: "power3.in",
                delay: 3,
                left: '19vw',
                top: '4vw',
                duration: 1,
            });

            gsap.to(wheel, {
                duration: 1,
                top: '4vw',
                left: '0vw',
                scale: 1,
                ease: "power3.in",
                delay: 3,
                onComplete: () => {
                    numberOutput.html(numberWinner);
                    finish = 0;
                    while(finish === 0 ){
                        checkEnd();
                       // console.log("cpt: "+finish);
                    }
                   
                    initTimeToLeft();
                    buscarBonus();
                  //  numberWinner = Math.floor(Math.random() * 36);
                }
            });
        }
    });
}

function updateColor(num) {
    //start build color from number
    var isred = false;
    if(parseInt(num) == 0){
        gsap.to(numberOutput, { background: 'radial-gradient(circle at 5px 5px,#1a7332, green)' });
    }
    else{
        isred = false;
        for (let k = 0; k <red.length; k++) {
            if (parseInt(num) == parseInt(red[k])) {
                gsap.to(numberOutput, { background: 'radial-gradient(circle at 5px 5px,#ad2b2b, red)' });
                isred = true;
                break;
            }
        }
        if(!isred){
           gsap.to(numberOutput, { background: 'radial-gradient(circle at 5px 5px,#2b2b2b, black)' }); 
        }
    }
    
    //end build color from number
}

function buscarDraw(){
    console.log("Generate draw");
    var draw = null;
    $.ajax({
            url:"generatedrawp",
            type:"GET",
            async: false,
            data:{
                            'coderace':coderace
                        },
            success:function(result){
                // Pour chaque résultat du tableau
                $.each(result, function(index, value){
                 
                    // on retrouve les paramètres qu'on avait fixé via l'API Json dans la servlet
                   numberWinner = value.drawnumbp;
                  // console.log('WINNER: '+numberWinner);
                   trouve_draw = true;
                });
            }
        });   
}

function buscarBonus(){
    console.log("Search Bonus");

    $.ajax({
            url:"managebonusp",
            type:"GET",
            async: false,
            data:{
                            'coderace':coderace
                        },
            success:function(result){
                // Pour chaque résultat du tableau
                $.each(result, function(index, value){
                   var bonus = value.bonusp;

                   $("#codep_bonus").empty(); 
                   $("#amountp_bonus").empty();
                   $("#coderacep_bonus").empty();

                   //console.log('WINNER BONUS: '+bonus);
                   if(bonus == 1){
                     $('.bloc_2').css('display','none');
                     $("#codep_bonus").prepend(value.codebonus); 
                     $("#amountp_bonus").prepend(value.montantbonus); 
                     $("#coderacep_bonus").prepend(value.partner+' Bravooooo!!!'); 
                     $('#myBonus').modal('show');
                     setTimeout(function(){
                         $('#myBonus').modal('hide');
                         $('.bloc_2').css('display','block');
                     }, 10000);
                   }
                });
            }
        });   
}

function checkEnd(){
    console.log("Check End");
    $.ajax({
            url:"checkendp",
            type:"GET",
            data:{
                            'coderace':coderace
                        },
            async:false,
            success:function(result){
                // Pour chaque résultat du tableau
                $.each(result, function(index, value){
                 
                    // on retrouve les paramètres qu'on avait fixé via l'API Json dans la servlet
                   finish = value.enddrawp;
                   //console.log('ENDDRAW P: '+finish);
                });
            }
        }); 

}
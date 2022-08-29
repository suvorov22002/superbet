let timespin;
function initTimeToLeft2() {
    recordDisplay();
    //console.log('INIT Timespin: '+timespin);
    if(timespin < 60) timespin = 60;

    let iminute = parseInt(timespin / 60);
    let isecond = timespin % 60;
    let minute;
    let seconde;

     //console.log('INIT minute: '+iminute+' SECOND: '+isecond);
    if(iminute !=0 && isecond == 0){
        minute = iminute - 1;
        seconde = 60;
    }
    else if(isecond !=0 ){
        minute = iminute;
        seconde = isecond;
    }

    let time = `${minute}:${seconde}`;

    //in second
    let total_time = minute == 0 ? seconde : minute * 60 + seconde;

    let timeElement = $('#minute_secnd');

    let time_box_left = $('#time_box_left');

    let height_time_box_left = 85;

    let current_heignt_time_left = 0;

    timeElement.text(time);

    let slideElement = $('.stat_box_slide');


    let timeLeft = gsap.timeline({ paused: true });
    timeLeft
        .to(time_box_left, {
            backgroundColor: 'red',
        })

    let timeIntervalId = setInterval(
        () => {
            //manage  slider
            // i apply transform each 10 seconds
            console.log('Current Timespin: '+timespin);
            // // slide to 2
            if (60 - seconde == 10 || 60 - seconde == 40) {
                slideElement.eq(0).animate({
                    'left': '-24.8vw'
                });
            }

            // //slide to 3
            if (60 - seconde == 20 || 60 - seconde == 50) {
                slideElement.eq(0).animate({
                    'left': '-50vw'
                });
            }

            // //slide to 1
            if (60 - seconde == 30 || seconde == 60) {
                slideElement.eq(0).animate({
                    'left': '0vw'
                });
            }
            // // end slider

            seconde--;

            if (seconde == 20 && minute == 0) {
                timeLeft.play();
            }

            if (seconde == 0) {
                minute--;
                if (minute >= 0) {
                    seconde = 60;
                }
            }

            if (minute < 0) {
                minute = 0;
            }

            time = seconde < 10 ? `${minute}:0${seconde}` : `${minute}:${seconde}`;
            timeElement.text(time);
            current_heignt_time_left += height_time_box_left / total_time;
            time_box_left.css('height', `${height_time_box_left - current_heignt_time_left}vh`);


            if (seconde == 0 && minute <= 0) {
                clearInterval(timeIntervalId);
                setTimeout(() => {
                    startSpin();
                    time_box_left.css('background-color','#fff15d')
                }, 1500)
            }
        }, 1000
    );
}

/*function recordDisplay(){
    $.ajax({
        url:"recordisplayp",
        type:"GET",
        async: false,
         data:{
                'coderace':coderace
            },
        success:function(result){
            $.each(result, function(index, value){
                timespin = value.timespin;
                //console.log('Timespin TIEMTJS: '+timespin);
            });
        }
      });
}*/

function initTimeToLeft() {
    //recordDisplay();
    //restoreDatas();
    console.log('INIT Timespin: '+timespin);
    if(timespin < 60) timespin = 60;

    let iminute = parseInt(timespin / 60);
    let isecond = timespin % 60;
    let minute;
    let seconde;

   // console.log('INIT minute: '+iminute+' SECOND: '+isecond);
    if(iminute !=0 && isecond == 0){
        minute = iminute - 1;
        seconde = 60;
    }
    else if(isecond !=0 ){
        minute = iminute;
        seconde = isecond;
    }

    
    let time = `${minute}:${seconde}`;
    
    //in second
    let total_time = minute == 0 ? seconde : minute * 60 + seconde;

    let timeElement = $('#minute_secnd');

    let time_box_left = $('#time_box_left');

    let height_time_box_left = 85;

    let current_heignt_time_left = 0;

    timeElement.text(time);

    let slideElement = $('.stat_box_slide');

    let timeLeft = gsap.timeline({ paused: true });
    timeLeft
        .to(time_box_left, {
            backgroundColor: 'red',
        })

    let timeIntervalId = setInterval(
        () => {

            iminute = parseInt(timespin / 60);
            isecond = timespin % 60;
            
            refreshDatas();
         //   console.log('INIT minute: '+iminute+' SECOND: '+isecond);
            if(iminute !=0 && isecond == 0){
                minute = iminute - 1;
                seconde = 60;
            }
            else if(isecond !=0 || (isecond==0 && iminute==0)){
                minute = iminute;
                seconde = isecond;
            }
            
            //manage  slider
            // i apply transform each 10 seconds

            // // slide to 2
            if (60 - seconde == 10 || 60 - seconde == 40) {
                slideElement.eq(0).animate({
                    'left': '-24.8vw'
                });
            }

            // //slide to 3
            if (60 - seconde == 20 || 60 - seconde == 50) {
                slideElement.eq(0).animate({
                    'left': '-50vw'
                });
            }

            // //slide to 1
            if (60 - seconde == 30 || seconde == 60) {
                slideElement.eq(0).animate({
                    'left': '0vw'
                });
            }
            // // end slider

          //  seconde--;

            if (seconde == 20 && minute == 0) {
                timeLeft.play();
            }

           /* if (seconde == 0) {
               // minute--;
                if (minute >= 0) {
                    seconde = 60;
                }
            }*/

            if (minute < 0) {
                minute = 0;
            }

            time = seconde < 10 ? `${minute}:0${seconde}` : `${minute}:${seconde}`;
            timeElement.text(time);
            current_heignt_time_left += height_time_box_left / total_time;
            time_box_left.css('height', `${height_time_box_left - current_heignt_time_left}vh`);


            if (seconde == 0 && minute <= 0) {
                clearInterval(timeIntervalId);
                setTimeout(() => {
                    //console.log('compteur off')
                    startSpin();
                    time_box_left.css('background-color','#fff15d')
                }, 1500)
            }
        }, 1000
    );
}


initTimeToLeft();


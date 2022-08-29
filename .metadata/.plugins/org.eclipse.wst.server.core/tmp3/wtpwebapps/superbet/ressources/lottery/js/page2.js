let page2 = $('#page2');
//let page2 = page2.jsp;
let page2content =
    "<div id='page2-bloc-left'>" +
    "<div id='page2-shuffle'>" +
    "<div id ='shuffle-container'>" +
    "<img alt='shuffle' src='ressources/lottery/assets/shuffle.gif'/>" +
    "</div>" +
    "<div id='outputZoom'>" +
    "<span class='number'></span>" +
    "</div>" +
    "<div id='multiplicateur'>" +
    "<span class=''></span>" +
    "</div>" +
    "</div>" +
    "<div id='page2-jackpot'>" +
    "<span class='label label-bloc1'>jackpot</span>" +
    "<div class='jackpot-container-page-2'>" +
    "<span class='divide2-jackpotPage2' id='divide2-jackpot'>" +
	"</span>" +
    "</div>" +
    "</div>" + 
    "<div id='page2-tirage'>" +
    "<div class='tirage-item'>" +
    "<span class='label-bloc1'>tirage</span>" +
    "<span class='value' id='drawnumb'></span>" +
    "</div>" +
    "<div class='tirage-item'>" +
    "<span class='label-bloc1'>M</span>" +
    "<span id='multi' class='value'>-</span>" +
    "</div>" +
    "</div>" +
    "</div>" +

    "<div id='page2-bloc-right'>" +
    "<div id='number-output-container'>" +
    "<div class='number-output'>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "</div>" +
    "<div class='number-output'>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "<div class='number-output-item'>" +
    "</div>" +
    "</div>" +
    "</div>" +



    "<div id='number-input-container'>" +

    "<div class='number-input'>" +
    "<div id='num1' class='number-input-item'>" +
    "<span class='number'>1</span>" +
    "</div>" +
    "<div id='num2'  class='number-input-item'>" +
    "<span class='number'>2</span>" +
    "</div>" +
    "<div id='num3'  class='number-input-item'>" +
    "<span class='number'>3</span>" +
    "</div>" +
    "<div id='num4'  class='number-input-item'>" +
    "<span class='number'>4</span>" +
    "</div>" +
    "<div id='num5'  class='number-input-item'>" +
    "<span class='number'>5</span>" +
    "</div>" +
    "<div id='num6'  class='number-input-item'>" +
    "<span class='number'>6</span>" +
    "</div>" +
    "<div id='num7'  class='number-input-item'>" +
    "<span class='number'>7</span>" +
    "</div>" +
    "<div id='num8' class='number-input-item'>" +
    "<span class='number'>8</span>" +

    "</div>" +
    "<div id='num9' class='number-input-item'>" +
    "<span class='number'>9</span>" +
    "</div>" +
    "<div id='num10' class='number-input-item'>" +
    "<span class='number'>10</span>" +
    "</div>" +
    "</div>" +
    "<div class='number-input'>" +
    "<div id='num11'  class='number-input-item'>" +
    "<span class='number'>11</span>" +
    "</div>" +
    "<div id='num12' class='number-input-item'>" +
    "<span class='number'>12</span>" +
    "</div>" +
    "<div id='num13' class='number-input-item'>" +
    "<span class='number'>13</span>" +
    "</div>" +
    "<div id='num14' class='number-input-item'>" +
    "<span class='number'>14</span>" +

    "</div>" +
    "<div id='num15' class='number-input-item'>" +
    "<span class='number'>15</span>" +

    "</div>" +
    "<div id='num16' class='number-input-item'>" +
    "<span class='number'>16</span>" +

    "</div>" +
    "<div id='num17' class='number-input-item'>" +
    "<span class='number'>17</span>" +

    "</div>" +
    "<div id='num18' class='number-input-item'>" +
    "<span class='number'>18</span>" +

    "</div>" +
    "<div id='num19' class='number-input-item'>" +
    "<span class='number'>19</span>" +

    "</div>" +
    "<div id='num20' class='number-input-item'>" +
    "<span class='number'>20</span>" +

    "</div>" +
    "</div>" +
    "<div class='number-input'>" +
    "<div id='num21' class='number-input-item'>" +
    "<span class='number'>21</span>" +

    "</div>" +
    "<div id='num22' class='number-input-item'>" +
    "<span class='number'>22</span>" +
    "</div>" +
    "<div id='num23' class='number-input-item'>" +
    "<span class='number'>23</span>" +
    "</div>" +
    "<div id='num24' class='number-input-item'>" +
    "<span class='number'>24</span>" +
    "</div>" +
    "<div id='num25' class='number-input-item'>" +
    "<span class='number'>25</span>" +
    "</div>" +
    "<div id='num26' class='number-input-item'>" +
    "<span class='number'>26</span>" +
    "</div>" +
    "<div id='num27' class='number-input-item'>" +
    "<span class='number'>27</span>" +
    "</div>" +
    "<div id='num28' class='number-input-item'>" +
    "<span class='number'>28</span>" +
    "</div>" +
    "<div id='num29' class='number-input-item'>" +
    "<span class='number'>29</span>" +
    "</div>" +
    "<div id='num30' class='number-input-item'>" +
    "<span class='number'>30</span>" +
    "</div>" +
    "</div>" +
    "<div class='number-input'>" +
    "<div id='num31' class='number-input-item'>" +
    "<span class='number'>31</span>" +
    "</div>" +
    "<div id='num32' class='number-input-item'>" +
    "<span class='number'>32</span>" +
    "</div>" +
    "<div id='num33' class='number-input-item'>" +
    "<span class='number'>33</span>" +
    "</div>" +
    "<div id='num34' class='number-input-item'>" +
    "<span class='number'>34</span>" +
    "</div>" +
    "<div id='num35' class='number-input-item'>" +
    "<span class='number'>35</span>" +
    "</div>" +
    "<div id='num36' class='number-input-item'>" +
    "<span class='number'>36</span>" +
    "</div>" +
    "<div id='num37' class='number-input-item'>" +
    "<span class='number'>37</span>" +
    "</div>" +
    "<div id='num38' class='number-input-item'>" +
    "<span class='number'>38</span>" +
    "</div>" +
    "<div id='num39' class='number-input-item'>" +
    "<span class='number'>39</span>" +
    "</div>" +
    "<div id='num40' class='number-input-item'>" +
    "<span class='number'>40</span>" +
    "</div>" +
    "</div>" +
    "<div class='number-input'>" +
    "<div id='num41' class='number-input-item'>" +
    "<span class='number'>41</span>" +
    "</div>" +
    "<div id='num42' class='number-input-item'>" +
    "<span class='number'>42</span>" +
    "</div>" +
    "<div id='num43' class='number-input-item'>" +
    "<span class='number'>43</span>" +
    "</div>" +
    "<div id='num44' class='number-input-item'>" +
    "<span class='number'>44</span>" +
    "</div>" +
    "<div id='num45' class='number-input-item'>" +
    "<span class='number'>45</span>" +
    "</div>" +
    "<div id='num46' class='number-input-item'>" +
    "<span class='number'>46</span>" +
    "</div>" +
    "<div id='num47' class='number-input-item'>" +
    "<span class='number'>47</span>" +
    "</div>" +
    "<div id='num48' class='number-input-item'>" +
    "<span class='number'>48</span>" +
    "</div>" +
    "<div id='num49' class='number-input-item'>" +
    "<span class='number'>49</span>" +
    "</div>" +
    "<div id='num50' class='number-input-item'>" +
    "<span class='number'>50</span>" +
    "</div>" +
    "</div>" +
    "<div class='number-input'>" +
    "<div id='num51' class='number-input-item'>" +
    "<span class='number'>51</span>" +
    "</div>" +
    "<div id='num52' class='number-input-item'>" +
    "<span class='number'>52</span>" +
    "</div>" +
    "<div id='num53' class='number-input-item'>" +
    "<span class='number'>53</span>" +
    "</div>" +
    "<div id='num54' class='number-input-item'>" +
    "<span class='number'>54</span>" +
    "</div>" +
    "<div id='num55' class='number-input-item'>" +
    "<span class='number'>55</span>" +
    "</div>" +
    "<div id='num56' class='number-input-item'>" +
    "<span class='number'>56</span>" +
    "</div>" +
    "<div id='num57' class='number-input-item'>" +
    "<span class='number'>57</span>" +
    "</div>" +
    "<div id='num58' class='number-input-item'>" +
    "<span class='number'>58</span>" +
    "</div>" +
    "<div id='num59' class='number-input-item'>" +
    "<span class='number'>59</span>" +
    "</div>" +
    "<div id='num60' class='number-input-item'>" +
    "<span class='number'>60</span>" +
    "</div>" +
    "</div>" +
    "<div class='number-input'>" +
    "<div id='num61' class='number-input-item'>" +
    "<span class='number'>61</span>" +
    "</div>" +
    "<div id='num62' class='number-input-item'>" +
    "<span class='number'>62</span>" +
    "</div>" +
    "<div id='num63' class='number-input-item'>" +
    "<span class='number'>63</span>" +
    "</div>" +
    "<div id='num64' class='number-input-item'>" +
    "<span class='number'>64</span>" +
    "</div>" +
    "<div id='num65' class='number-input-item'>" +
    "<span class='number'>65</span>" +
    "</div>" +
    "<div id='num66' class='number-input-item'>" +
    "<span class='number'>66</span>" +
    "</div>" +
    "<div id='num67' class='number-input-item'>" +
    "<span class='number'>67</span>" +
    "</div>" +
    "<div id='num68' class='number-input-item'>" +
    "<span class='number'>68</span>" +
    "</div>" +
    "<div id='num69' class='number-input-item'>" +
    "<span class='number'>69</span>" +
    "</div>" +
    "<div id='num70' class='number-input-item'>" +
    "<span class='number'>70</span>" +
    "</div>" +
    "</div>" +
    "<div class='number-input'>" +
    "<div id='num71' class='number-input-item'>" +
    "<span class='number'>71</span>" +
    "</div>" +
    "<div id='num72' class='number-input-item'>" +
    "<span class='number'>72</span>" +
    "</div>" +
    "<div id='num73' class='number-input-item'>" +
    "<span class='number'>73</span>" +
    "</div>" +
    "<div id='num74' class='number-input-item'>" +
    "<span class='number'>74</span>" +
    "</div>" +
    "<div id='num75' class='number-input-item'>" +
    "<span class='number'>75</span>" +
    "</div>" +
    "<div id='num76' class='number-input-item'>" +
    "<span class='number'>76</span>" +
    "</div>" +
    "<div id='num77' class='number-input-item'>" +
    "<span class='number'>77</span>" +
    "</div>" +
    "<div id='num78' class='number-input-item'>" +
    "<span class='number'>78</span>" +
    "</div>" +
    "<div id='num79' class='number-input-item'>" +
    "<span class='number'>79</span>" +
    "</div>" +
    "<div id='num80' class='number-input-item'>" +
    "<span class='number'>80</span>" +
    "</div>" +
    "</div>" +
    "</div>" +
    "</div>";



//let arrayNumroSortant = [35, 43, 31, 50, 68, 36, 72, 8, 80, 16, 24, 33, 56, 74, 46, 28, 78, 11, 45, 4];

function setarrayNumroSortant() {
    //set number in first row
    let page2OutputWinnner1 = $('#page2 .number-output');
    let children1 = page2OutputWinnner1.eq(0).children();
    for (let i = 0; i < children1.length; i++) {
        let currentChildren1 = children1.eq(i);
         //console.log("first row:"+arrayNumroSortant[i]);
        currentChildren1.append("<span class='numOutput num" + arrayNumroSortant[i] + "'>" + arrayNumroSortant[i] + "</span>");
    }

    //set number in second row
    let page2OutputWinnner2 = $('#page2 .number-output');
    let children2 = page2OutputWinnner2.eq(1).children()
    for (let i = 0; i < children2.length; i++) {
        let currentChildren2 = children2.eq(i);
         //console.log("second row:"+arrayNumroSortant[i + 10]);
        currentChildren2.append("<span class='numOutput num" + arrayNumroSortant[i + 10] + "'>" + arrayNumroSortant[i + 10] + "</span>");
    }
}

function animationCircle() {
    let l = 0;
    let page2OutputWinnner1 = $('#page2 .number-output');
    let children1 = page2OutputWinnner1.eq(0).children();
    var tps = 50;

    if(time == 185 && gamestate != 1 && gamestate != 4){
 //   if(gamestate == 3){
        tps = 1;
    }
    
    let intervalIdpage2 = setInterval(
        () => {

            children1.eq(l).css('visibility', 'visible');
            children1.eq(l).addClass('animated bounceIn');
           
            l++;

            if (l > children1.length) {
                clearInterval(intervalIdpage2);

                let m = 0;

                let page2OutputWinnner2 = $('#page2 .number-output');
                let children2 = page2OutputWinnner2.eq(1).children();

                if(time == 185 && gamestate != 1 && gamestate != 4){
                    tps = 1;
                }
                let intervalId2page2 = setInterval(
                    () => {

                        children2.eq(m).css('visibility', 'visible');
                        children2.eq(m).addClass('animated bounceIn');
                        m++;

                        if (m > children2.length) {
                            clearInterval(intervalId2page2);

                            animationSquare();
                        }
                    }, tps
                );
                arrayInterval.push(intervalId2page2);
            }
        }, tps
    )
    arrayInterval.push(intervalIdpage2);


}

function animationSquare() {
    let numberInput = $('#page2 .number-input');
    let k = 0;
    
    //setarrayNumroSortant();
    
    //first row
    let firstChildrenNumberInput = numberInput.eq(0);
    let i = 0;
    let tp = 50;

    if(time == 185 && gamestate != 1 && gamestate != 4){
       tp = 1;
    }

    let intervalId = setInterval(
        () => {
            firstChildrenNumberInput.children().eq(i).css('visibility', 'visible');
            firstChildrenNumberInput.children().eq(i).addClass('animated bounceIn')
            i++;
            if (i > firstChildrenNumberInput.children().length) {
                clearInterval(intervalId)
            }
        }, tp
    )
    arrayInterval.push(intervalId);
    

    let intervalId2 = setInterval(
        () => {
            let firstChildrenNumberInput = numberInput.eq(k + 1);
            let i = 0;

            let intervalId = setInterval(
                () => {
                    firstChildrenNumberInput.children().eq(i).css('visibility', 'visible');
                    firstChildrenNumberInput.children().eq(i).addClass('animated bounceIn')
                    i++;
                    if (i > firstChildrenNumberInput.children().length) {
                        clearInterval(intervalId)
                    }
                }, tp
            )
            arrayInterval.push(intervalId);
            

            k++;
            if (k > numberInput.length) {
                clearInterval(intervalId2);
                var tiemp = 200;
                if(time == 185 && gamestate != 1 && gamestate != 4){
                    tiemp = 1;
                }
                        
                setTimeout(
                    () => {
                        
                        animationShowJackpotTirage();
                        var __tps = 2000;
                        if(time == 185 && gamestate != 1 && gamestate != 4){
                            _tps = 1;
                        }
                        setTimeout(
                            () => {
                                $('#shuffle-container').css('visibility', 'visible');
                                $('#shuffle-container').addClass('animated fadeIn');
                                var _tps = 5000;
                                if(time == 185 && gamestate != 1 && gamestate != 4){
                                    _tps = 1;
                                }
                                setTimeout(
                                    () => {
                                        
                                        let n = 1;
                                        if(time == 185 && gamestate != 1 && gamestate != 4){
                                            n = _str_combi.length - 1;
                                            for (let ii = 0; ii < n; ii++) {
                                              console.log('_str_combi '+_str_combi[ii])
                                              animationAdd(_str_combi[ii]);
                                            }
                                            
                                        }
                                        else{
                                            console.log(' animationOutputZoom '+arrayNumroSortant[0])
                                            if(arrayNumroSortant[0] != undefined)
                                               animationOutputZoom(arrayNumroSortant[0]);
                                        }

                                        let intervalId4 = setInterval(
                                            () => {
                                                console.log(' animationOutputZoom '+arrayNumroSortant[n])
                                                 if(arrayNumroSortant[n] != undefined)
                                                    animationOutputZoom(arrayNumroSortant[n]);
                                                n++;
                                                if(n > 5 && n < 7) startDraw(3);
                                                if (n > arrayNumroSortant.length - 1) {
                                                    clearInterval(intervalId4);

                                                    setTimeout(
                                                        () => {
                                                            animationOutputMultiplicateur();
                                                            //mise a jour de la fin du tirage(colone started)
                                                            
                                                            setTimeout(
                                                                () => {

                                                                    animationHideJackpotTirage();
                                                                }, 4000
                                                            )
                                                        }, 5000
                                                    )

                                                }
                                            }, 5000
                                        )
                                    }, _tps
                                )
                            }, __tps
                        )
                    }, tiemp
                )
            }
        }, 500
    )
    arrayInterval.push(intervalId2);


}

let currentNumber3 = null;
let arrayJackpot3 = [
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

function animationJackpotCountNumberPartIntPage2(j, k) {
    let jackpotNumber = $(".numberJackpot-page-2").eq(k);
    let coordY = null;

    if (currentNumber3 == null) {
        coordY = -65 * j;
    } else {
        coordY = -65 * (j - currentNumber3);
        currentNumber3 = j
    }

    if (j == 0) {
        coordY = 0;
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");

    } else {
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");
    }
}

function animationJackpotCountNumberPartDecimalPage2(j, k) {
    let jackpotNumber = $(".numberJackpot-page-2-2").eq(k);
    let coordY = null;

    if (currentNumber3 == null) {
        coordY = -65 * j;
    } else {
        coordY = -65 * (j - currentNumber3);
        currentNumber3 = j
    }

    if (j == 0) {
        coordY = 0;
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");

    } else {
        jackpotNumber.css("transform", "translateY(" + coordY + "px)");
    }
}

function setNumberInjackPotSecondPage2() {
    let i = 0;
    let setNumberInJackpotId = setInterval(
        () => {
            let item = arrayJackpot3[i].toString();
            //first number
            animationJackpotCountNumberPartIntPage2(parseInt(item[0]), 0);

            //second
            animationJackpotCountNumberPartIntPage2(parseInt(item[1]), 1);

            //third
            animationJackpotCountNumberPartIntPage2(parseInt(item[2]), 2);

            //fourth
            animationJackpotCountNumberPartIntPage2(parseInt(item[3]), 3);

            //decimal 1
            animationJackpotCountNumberPartDecimalPage2(parseInt(item[5]), 0);

            //decimal 2
            animationJackpotCountNumberPartDecimalPage2(parseInt(item[6]), 1);

            i++;
            //console.log(i, arrayJackpot3.length);
            if (i == arrayJackpot3.length) {
                clearInterval(setNumberInJackpotId);
            }
        }, 100
    )
}

function setNumberInjackPotFirstPage2() {
    let item = arrayJackpot3[0].toString();
    //first number
    animationJackpotCountNumberPartIntPage2(parseInt(item[0]), 0);

    //second
    animationJackpotCountNumberPartIntPage2(parseInt(item[1]), 1);

    //third
    animationJackpotCountNumberPartIntPage2(parseInt(item[2]), 2);

    //fourth
    animationJackpotCountNumberPartIntPage2(parseInt(item[3]), 3);

    //decimal 1
    animationJackpotCountNumberPartDecimalPage2(parseInt(item[5]), 0);

    //decimal 2
    animationJackpotCountNumberPartDecimalPage2(parseInt(item[6]), 1);

}

function animationOutputZoom(number) {
    if(number != undefined || number != ""){
         let outputZoom = $('#page2 #outputZoom');
        outputZoom.children().eq(0).text(number);
        outputZoom.css('visibility', 'visible');
        // outputZoom.removeClass('animated bounceOut')
        outputZoom.addClass('animated zoomIn')

        if (number >= 1 && number <= 20) {
            outputZoom.css('background', 'radial-gradient(circle at 5px 5px,#1a7332, green)')
        }

        if (number >= 21 && number <= 40) {
            outputZoom.css('background', 'radial-gradient(circle at 5px 5px,#336dff, blue)');
        }

        if (number >= 41 && number <= 60) {
            outputZoom.css('background', 'radial-gradient(circle at 5px 5px,#ad2b2b, red)');
        }

        if (number >= 61 && number <= 80) {
            outputZoom.css('background', 'radial-gradient(circle at 5px 5px,#f7d71e, #ebb746)');
        }

        setTimeout(
            () => {
                animationOutputZoomHide(number);
            }, 2000
        )
        }
   
}

function animationOutputMultiplicateur() {
    let multi = $('#page2 #multiplicateur');
    multi.children().eq(0).text(multiplicateur+'x');
    multi.css('visibility', 'visible');
    multi.removeClass('animated bounceOut')
    multi.addClass('animated bounceIn')
    //ndDraw();
    startDraw(4);
    setTimeout(
        () => {
            animationMultiHide();
        }, 2000
    )
}

function endDraw(){

    let interStart = setInterval(async () => {
        console.log("end-draw");
                if(urlServeur != undefined){
                    var url = urlServeur+'/end-draw/'+coderace;
                    const response = await fetch(url);
                    const myJson = await response.json(); //extract JSON from the http response
                    clearInterval(interStart);
                }
                
            },300);    
}


function animationMultiHide(number) {
    let outputZoom = $('#page2 #multiplicateur');
    outputZoom.addClass('animated bounceOut');

    setTimeout(
        () => {
            let currentMulti = $('#page2 #multi');
            currentMulti.text(multiplicateur+'x');
        }, 1000
    )
}

function animationOutputZoomHide(number) {
    let outputZoom = $('#page2 #outputZoom');
    outputZoom.removeClass('animated zoomIn')
    outputZoom.addClass('animated bounceOut');

    setTimeout(
        () => {
            let currentInput = $('#page2 #num' + number);

            let currentOutput = $('span.num' + number);

            currentInput.css('color', 'white')

            if (number >= 1 && number <= 20) {
                currentInput.css('background', 'radial-gradient(circle at 5px 5px,#1a7332, green)');
                currentOutput.css('background', 'radial-gradient(circle at 5px 5px,#1a7332, green)');
            }

            if (number >= 21 && number <= 40) {
                currentInput.css('background', 'radial-gradient(circle at 5px 5px,#336dff, blue)');
                currentOutput.css('background', 'radial-gradient(circle at 5px 5px,#336dff, blue)');
            }

            if (number >= 41 && number <= 60) {
                currentInput.css('background', 'radial-gradient(circle at 5px 5px,#ad2b2b, red)');
                currentOutput.css('background', 'radial-gradient(circle at 5px 5px,#ad2b2b, red)');
            }

            if (number >= 61 && number <= 80) {
                currentInput.css('background', 'radial-gradient(circle at 5px 5px,#f7d71e, #ebb746)');
                currentOutput.css('background', 'radial-gradient(circle at 5px 5px,#f7d71e, #ebb746)');
            }
        }, 1000
    )

}

function animationAdd(number) {
    
    let currentInput = $('#page2 #num' + number);

    let currentOutput = $('span.num' + number);

    currentInput.css('color', 'white')

    if (number >= 1 && number <= 20) {
        currentInput.css('background', 'radial-gradient(circle at 5px 5px,#1a7332, green)');
        currentOutput.css('background', 'radial-gradient(circle at 5px 5px,#1a7332, green)');
    }

    if (number >= 21 && number <= 40) {
        currentInput.css('background', 'radial-gradient(circle at 5px 5px,#336dff, blue)');
        currentOutput.css('background', 'radial-gradient(circle at 5px 5px,#336dff, blue)');
    }

    if (number >= 41 && number <= 60) {
        currentInput.css('background', 'radial-gradient(circle at 5px 5px,#ad2b2b, red)');
        currentOutput.css('background', 'radial-gradient(circle at 5px 5px,#ad2b2b, red)');
    }

    if (number >= 61 && number <= 80) {
        currentInput.css('background', 'radial-gradient(circle at 5px 5px,#f7d71e, #ebb746)');
        currentOutput.css('background', 'radial-gradient(circle at 5px 5px,#f7d71e, #ebb746)');
    }

}

function animationShowJackpotTirage() {
    setNumberInjackPotFirstPage2();
    let element1 = $('#page2-jackpot');
    let element2 = $('#page2-tirage');
    element1.css('visibility', 'visible');
    element2.css('visibility', 'visible');
    element1.addClass('animated bounceInUp');
    element2.addClass('animated bounceInUp');
    var tpss = 2500;

    if(time == 185 && gamestate != 1 && gamestate != 4){
        tpss = 1;
    }

    setTimeout(
        () => {
            setNumberInjackPotSecondPage2();
        }, tpss
    )

}

function animationHideJackpotTirage() {
    let element1 = $('#page2-jackpot');
    let element2 = $('#page2-tirage');
    element1.removeClass('animated bounceInUp');
    element2.removeClass('animated bounceInUp');
    element1.addClass('animated bounceOutDown');
    element2.addClass('animated bounceOutDown');
    setTimeout(
        () => {
            animationPage2HideSquare();
        }, 1000
    )
}

function page2animationHideNumeroOutput() {
    let i = 2;
    let intervalIdManageCirclePage2 = setInterval(
        () => {
            let currentContainerCircle = $('.number-output').eq(i);

            let j = 10;
            let intervalIdManageCirclePage2Item = setInterval(
                () => {
                    let currentOutputCircle = currentContainerCircle.children().eq(j);
                    currentOutputCircle.removeClass('bounceIn');
                    currentOutputCircle.addClass('animated zoomOut');
                    j--;
                    if (j < 0) {
                        clearInterval(intervalIdManageCirclePage2Item);

                        setTimeout(
                            () => {
                                buildPage3();
                            }, 2000
                        )
                    }
                }, 50
            )
            arrayInterval.push(intervalIdManageCirclePage2Item);
            i--;
            if (i < 0) {
                clearInterval(intervalIdManageCirclePage2);
            }
        }, 50
    );
    arrayInterval.push(intervalIdManageCirclePage2);
}

function animationPage2HideSquare() {
    let i = 0;
    let intervalIdManageSquarePage2 = setInterval(
        () => {
            let currentContainerSquare = $('.number-input').eq(i);

            let j = 10;
            let intervalIdManageSquarePage2Item = setInterval(
                () => {
                    let currentOutputSquare = currentContainerSquare.children().eq(j);
                    currentOutputSquare.removeClass('bounceIn');
                    currentOutputSquare.addClass('animated bounceOutDown');
                    j--;
                    if (j < 0) {
                        clearInterval(intervalIdManageSquarePage2Item);

                        setTimeout(
                            () => {
                                page2animationHideNumeroOutput();

                                setTimeout(
                                    () => {
                                        $('#shuffle-container').removeClass('fadeIn');
                                        $('#shuffle-container').addClass('animated fadeOut');
                                    }, 100
                                )
                            }, 100
                        )
                    }
                }, 50
            )
            arrayInterval.push(intervalIdManageSquarePage2Item);

            i++;
            if (i > $('.number-input').length) {
                clearInterval(intervalIdManageSquarePage2);
            }
        }, 20
    )
    arrayInterval.push(intervalIdManageSquarePage2);
}



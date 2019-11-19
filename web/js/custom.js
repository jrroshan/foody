var canvas = document.getElementById('fortuneCanvas');
var ctx= canvas.getContext('2d');
var wheelSound = new sound("audio/Rolling On Wood LOOP_SS 01.mp3");
function sound(src) {
    this.sound = document.createElement("audio");
    this.sound.src = src;
    this.sound.loop=true;
    this.sound.setAttribute("preload", "auto");
    this.sound.setAttribute("controls", "none");
    this.sound.style.display = "none";
    document.body.appendChild(this.sound);
    this.play = function(){
      this.sound.play();
    }
    this.stop = function(){
      this.sound.pause();
    }
  }

function drawArrow(){
    ctx.beginPath();
    ctx.moveTo(canvas.width/1.70-50,canvas.height/2);
    ctx.lineTo(canvas.width/1.70,canvas.height/2-5);
    ctx.lineTo(canvas.width/1.70,canvas.height/2+5);
    ctx.closePath();
    ctx.strokeStyle="silver";
    ctx.stroke();
    ctx.fillStyle="brown";
    ctx.fill();
}


var fortuneImg = new Image();
fortuneImg.src="images/food/fortuneFood.png";
fortuneImg.onloadend=spinFortuneDefault();

// var pizzaImg=new Image();
// pizzaImg.src="./images/food/pizza.jpg";
// var momoImg=new Image();
// momoImg.src="images/food/burger.jpg";
// var burgerImg=new Image();
// burgerImg.src="images/food/pizza.jpg";
// var chickenNuggetsImg=new Image();
// chickenNuggetsImg.src="./images/food/pizza.jpg";
// var chowMeinImg=new Image();
// chowMeinImg.src="./images/food/pizza.jpg";

// var center=
// {
//     x:canvas.width/2,
//     y:canvas.height/2
// };
/*
var wheel=()=>{
    this.food=["Momo","Pizza","Burger","Chow mein","Chicken nuggets"];
    this.pallet=[momoImg,pizzaImg,burgerImg,chowMeinImg,chickenNuggetsImg];
   
    this.radius=canvas.height/2-5;
    this.slices=(this.food).keys(data).length();
    this.sliceDeg=360/this.slices;
    this.drawSlice=function(i,deg){
        ctx.beginPath();
        ctx.fillStyle();
    }
};
var wheelO={
    food:["Momo","Pizza","Burger","Chow mein","Chicken nuggets"],
    pallet:[momoImg,pizzaImg,burgerImg,chowMeinImg,chickenNuggetsImg],
   
    radius:canvas.height/2-5,
    slices:function(){
        return this.food.length;
    },
    sliceDeg:function(){return 360/this.slices()},
    drawSlice:function(i,deg){
        ctx.beginPath();
        ctx.strokeStyle="brown";
        ctx.fillStyle=ctx.createPattern(this.pallet[i],"no-repeat");
        ctx.arc(center.x,center.y,wheel.radius,deg*Math.PI/100,(wheel.sliceDeg()+deg)*Math.PI/100);
        ctx.stroke();
        ctx.fill();
    }
};
var wheel=new Object(wheelO);
*/
var deg=0;
var wheelSpin=false;
var endDeg=0;
// document.onloadend=spinFortuneDefault();
function spinFortuneDefault(){
    drawWheel();
    if(!wheelSpin)
        requestAnimationFrame(spinFortuneDefault);
   
}
function spinStart(){
    endDeg=Math.random()*1750;
    setTimeout(spinFortune,1,false);
    wheelSound.play();
    wheelSpin=true;
}
function spinFortune(){
    drawWheel();
    if(endDeg-->deg)
        setTimeout(spinFortune,0,false);
    else{
        wheelSound.stop();
    }
}
function drawWheel(){
    ctx.clearRect(0,0,canvas.width,canvas.height);
    ctx.save();
    // ctx.beginPath();
    // ctx.arc(center.x,center.y,wheel.radius,0,2*Math.PI);
    // ctx.strokeStyle="gold";
    // ctx.stroke();
    // var deg=0;
    // for(var i=0;i<wheel.slices();i++){
    //     wheel.drawSlice(i,(deg++)*wheel.sliceDeg());
    // }
    ctx.translate(canvas.width/4,canvas.height/2);
    ctx.rotate(deg *Math.PI/180);
    ctx.drawImage(fortuneImg,0,0,fortuneImg.width,fortuneImg.height,-canvas.height/2,-canvas.height/2,canvas.height,canvas.height);
    //console.log(canvas.width + "  " +canvas.height);
    deg++;
    deg%=360;
    ctx.restore();
    fortuneFoodShow(deg);
    drawArrow();
    
}
function fortuneFoodShow(deg){
    var fortuneFoodList=["Chow Mein","Chicken Nuggets","Momo","Pizza","French Fries","Burger"];
    var fortuneFoodDescriptionList=[
        "Chow Mein is tasty! Far far away, far from the countries Vokalia and Consonantia, there live the blind texts.",
        "Chicken Nuggets is tasty! Far far away, far from the countries Vokalia and Consonantia, there live the blind texts.",
        "Momo is tasty! Far far away, far from the countries Vokalia and Consonantia, there live the blind texts.",
        "Pizza is tasty! Far far away, far from the countries Vokalia and Consonantia, there live the blind texts.",
        "French Fries is tasty! Far far away, far from the countries Vokalia and Consonantia, there live the blind texts.",
        "Burger is tasty! Far far away, far from the countries Vokalia and Consonantia, there live the blind texts."];
    
    if(deg<30) var i=Math.floor(Math.abs(360-30+deg)/60);
    else var i=Math.floor(Math.abs(deg-30)/60);
    document.getElementById("fortuneFoodName").innerHTML=fortuneFoodList[i];
    document.getElementById("fortuneFoodDescription").innerHTML=fortuneFoodDescriptionList[i];

}

var txtLabel = document.getElementById("txtLabel");
txtLabel.textContent="";
txtLabel.style.color="#d35400";
var txt=["Pizza","Seafood","Burgers","Drinks"];

var activeWordIndex=0;
var activeLetterIndex=0;

typeLetter();

function eraseWord(){    
    txtLabel.style.background="silver";
    window.setTimeout(function(){
        txtLabel.textContent ="";
        txtLabel.style.background="none";
        typeLetter();
    },500);
}

function typeLetter(){    
    window.setTimeout(function(){
        if(activeLetterIndex!=txt[activeWordIndex].length){
            txtLabel.textContent += txt[activeWordIndex][activeLetterIndex];
            activeLetterIndex++;
            typeLetter();
        } else{
            activeLetterIndex=0;
            activeWordIndex++;
            if(activeWordIndex==txt.length){
                activeWordIndex=0;
            }
            window.setTimeout(eraseWord,750);
        }
    },250);
}
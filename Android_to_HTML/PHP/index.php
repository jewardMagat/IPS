<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <Title>Android to HTML</Title>   
        <style>
                #myCanvas
                {
                    padding: 0;
                    margin: auto;
                    display: block;
                    border:1px solid black;
                }
        </style>
        <link rel="stylesheet" href="">
    </head>  
    <div id="show"></div>
    <script type="text/javascript" src="jquery.js"></script>
    <script>
        var test;
        var test2;
        var myArray = new Array();
        $(document).ready(function(){

            setInterval(function(){
                $('#show').load('data.php')
                getData();
            },250);
        });

        function getData(){
            $.ajax({
                type: 'POST',
                url: 'data.php',
                cache: false,
                success: function(data){
                    data = JSON.parse(data);
//                    console.log(data[0]["1"]+ ' im space ' +data[1]["2"]);
                    test = data[0]["1"];
                    test2 = data[1]["2"];
                }

            });
        }
        
        //CANVAS//
        var context;
        var x=100;
        var y=200;
        var dx=3;
        var dy=3;
        var rad = 20;

        function init()
        {
            context = myCanvas.getContext('2d');
            setInterval(draw,10);                
        }

        function draw()
        {
            context.clearRect(0,0,300,300);
            context.beginPath();
            context.fillStyle="#0000ff";
            //draws a circle of radius 20 at the coordinates 100,100 on the canvas
            context.arc(test2*3,300 - (test*3),rad,0,Math.PI*2, true);
            context.closePath();
            context.fill();            }
    </script>
        
    <body onload="init()";>       
        <canvas id="myCanvas" width="300" height="300"></canvas>
    </body>
</html>





    
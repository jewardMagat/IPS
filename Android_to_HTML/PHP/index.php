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
    
    <?php
        include("data.php");
        echo $num;
        $value = $num;
    ?>
    <script>
        var data;
        
        $(document).ready(function(){
//            alert("Heelo");
        
            setInterval(function(){
                location.reload();
                
                console.log(data);
//                $('#show').load('data.php');
            },100);
        });  
        
        
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
                var data = "<?php echo $value; ?>";
                context.clearRect(100,100,300,300);
                context.beginPath();
                context.fillStyle="#0000ff";
                //draws a circle of radius 20 at the coordinates 100,100 on the canvas
                context.arc(data*3,150,rad,0,Math.PI*2, true);
                context.closePath();
                context.fill();
                if(x<rad||x>280)
                    dx=-dx;
                if(y<rad||y>280)
                    dy=-dy;
                
                x+=dx;
                y+=dy;
            }
    </script>
    
<body onload="init()";>
    <canvas id="myCanvas" width="300" height="300"></canvas>
    

</body>
</html>



    
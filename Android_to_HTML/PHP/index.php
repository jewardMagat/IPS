<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <Title>Android to HTML</Title>
    <link rel="stylesheet" href="">
</head>
<body>
    <div id="show"></div>
    <script type="text/javascript" src="jquery.js"></script>
    <script>
        $(document).ready(function(){
//            alert("Heelo");
            setInterval(function(){
                $('#show').load('data.php')
            },1000);
        });
    </script>
</body>
</html>



    
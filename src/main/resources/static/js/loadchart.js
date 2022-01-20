(function($) {
    "use strict"


    //exemple appel
    //<div class="card-body">
    //<canvas id="barChart_1"></canvas>
   //</div>

//dual line chart
function setlinechart(className,data){
    const lineChart = document.getElementById(className).getContext('2d');
    

    new Chart(lineChart, {
        type: 'line',
        data: {
            defaultFontFamily: 'Poppins',
            labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","August","Sept","Oct","Nov","Dec"],
            datasets: [
                {
                    label: "Stat Gain Annuel",
                    data: [data[0]['Janvier'],data[0]['Fevrier'],data[0]['Mars'],data[0]['Avril'],data[0]['Mai'],data[0]['Juin'],data[0]['Juillet'],data[0]['Aout'],data[0]['Septembre'],data[0]['Octobre'],data[0]['Novembre'],data[0]['Decembre']],
                    borderColor: lineChart_3gradientStroke1,
                    borderWidth: "2",
                    backgroundColor: 'transparent', 
                    pointBackgroundColor: 'rgba(26, 51, 213, 0.5)'
                }
            ]
        },
        options: {
            legend: false, 
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true, 
                        max: 500, 
                        min: 0, 
                        stepSize: 5, 
                        padding: 5
                    }
                }],
                xAxes: [{ 
                    ticks: {
                        padding: 5
                    }
                }]
            }
        }
    });
}

            $.get("http://localhost/sagoor2/index.php/Stat/stat")
                .done(function(data,text,jqxhr){
                    /*Dessinner les charts*/
                    var lista = JSON.parse(data);
                    console.log(lista);
                    setlinechart("lineChart_3",lista[0]);
                })
                .fail(function(jqxhr){
                    alert('An error, please insert data');
                });

})(jQuery);
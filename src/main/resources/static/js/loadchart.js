(function($) {
    "use strict"


    //exemple appel
    //<div class="card-body">
    //<canvas id="barChart_1"></canvas>
   //</div>

function setBarChart(className,data,label){
    const barChart_2 = document.getElementById(className).getContext('2d');
    const barChart_2gradientStroke = barChart_2.createLinearGradient(0, 0, 0, 250);
    barChart_2gradientStroke.addColorStop(0, "rgba(26, 51, 213, 1)");
    barChart_2gradientStroke.addColorStop(1, "rgba(26, 51, 213, 0.5)");

    barChart_2.height = 100;

    new Chart(barChart_2, {
        type: 'bar',
        data: {
            defaultFontFamily: 'Poppins',
            labels: label,
            datasets: [
                {
                    label: "My First dataset",
                    data: data,
                    borderColor: barChart_2gradientStroke,
                    borderWidth: "0",
                    backgroundColor: barChart_2gradientStroke, 
                    hoverBackgroundColor: barChart_2gradientStroke
                }
            ]
        },
        options: {
            legend: false, 
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }],
                xAxes: [{
                    // Change here
                    barPercentage: 0.5
                }]
            }
        }
    });
}

//dual line chart
function setlinechart(className,data){
   const lineChart_1 = document.getElementById(className).getContext('2d');
    
    lineChart_1.height = 100;

    new Chart(lineChart_1, {
        type: 'line',
        data: {
            defaultFontFamily: 'Poppins',
            labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Sept","Oct","Nov","Dec"],
            datasets: [
                {
                    label: "My First dataset",
                    data: data,
                    borderColor: 'rgba(56, 164, 248, 1)',
                    borderWidth: "2",
                    backgroundColor: 'transparent',  
                    pointBackgroundColor: 'rgba(56, 164, 248, 1)'
                }
            ]
        },
        options: {
            legend: false, 
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true, 
                        max: 10, 
                        min: 0, 
                        stepSize: 1, 
                        padding: 10
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

function setpiechart(className,data){
 const doughnut_chart = document.getElementById(className).getContext('2d');
    // doughnut_chart.height = 100;
    new Chart(doughnut_chart, {
        type: 'doughnut',
        data: {
            defaultFontFamily: 'Poppins',
            datasets: [{
                data: data,
                borderWidth: 0, 
                backgroundColor: [
                    "rgba(53, 255, 26, 0.8)",
                    "rgba(255, 66, 0, 0.8)",
                    "rgba(6, 173, 238, 0.8)"
                ],
                hoverBackgroundColor: [
                    "rgba(56, 25, 120, .5)",
                    "rgba(19,20, 100, .4)",
                    "rgba(2, 1, 128, .3)"
                ]

            }],
             labels: [
                 "En Attente",
                 "En cours",
                 "Terminé"

             ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

            $.get("/wb/signalement/stat")
                .done(function(data,text,jqxhr){
                    /*Dessinner les charts*/
                    var string = JSON.stringify(data);
                    var lista = JSON.parse(string);
                    console.log(lista);
                    setlinechart("lineChartDemo",lista[0]);
                    setpiechart("pieChartDemo",lista[2]);
                    setBarChart("barChartDemo",lista[1],lista[3])
                })
                .fail(function(jqxhr){
                    alert('An error, please insert data');
                });

})(jQuery);
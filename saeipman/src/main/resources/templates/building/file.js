google.charts.load('current', {
    'packages': ['corechart']
});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    // AJAX 요청을 통해 데이터 가져오기
    $.ajax({
        url: ctxPath + '/chart',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log(data, "데이터"); // 반환된 데이터 출력
            // 데이터 준비
            var chartData = [['Month', 'Building Name', 'Total Revenue']]; // 레이블은 대문자 사용 가능

            $.each(data, function(index, row) {
                chartData.push([row.month, row.buildingName, parseFloat(row.totalRevenue)]); // 데이터 필드는 소문자로 접근
            });

            // 차트 데이터 생성
            var dataTable = google.visualization.arrayToDataTable(chartData);

            var options = {
                title: '최근 3개월 건물 수익',
                subtitle: '상위 3개 건물 수익',
                hAxis: {
                    title: 'Month'
                },
                vAxis: {
                    title: 'Total Revenue'
                },
                bars: 'vertical' // 수직 막대 차트로 설정
            };

            // 차트 그리기
            var chart = new google.visualization.ColumnChart(document.getElementById('columnchart_material'));
            chart.draw(dataTable, options);
        },
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}
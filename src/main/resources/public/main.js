
 const gridOptions = {
      suppressClickEdit: true,
      suppressHorizontalScroll: true,
      columnDefs: [
           {
                headerName: "Order Type",
                field: "type",
                editable: false
           },
           {
               headerName: "Qty",
               field: "quantity",
               editable: false
           },
           {
               field: "servedBy",
               editable: false
           },
           {
                field: "amount",
                editable: false
           },
           {
                headerName: "Payment Method",
                field: "paymentMethod",
                editable: false
           },
           {
                field: "status",
                editable: false
           }
         ],
         defaultColDef: {editable: false, sortable: true, filter: true},
         rowSelection: 'multiple',
         animateRows: true
       };

  const eGridDiv = document.getElementById("ordersGroupView");
  new agGrid.Grid(eGridDiv, gridOptions);

  fetch("http://localhost:8080/dinehouse/api/v1/daily-report/orders")
  .then(response => response.json())
  .then(data => {
     gridOptions.api.sizeColumnsToFit();
     gridOptions.api.setRowData(data);
  });


  var mainListDiv = document.getElementById("mainListDiv"),
      mediaButton = document.getElementById("mediaButton");

  mediaButton.onclick = function () {
      "use strict";
      mainListDiv.classList.toggle("show_list");
      mediaButton.classList.toggle("active");
  };
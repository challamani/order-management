 const gridOptions = {
         suppressClickEdit: true,
         suppressHorizontalScroll: true,
      columnDefs: [
           {
                headerName: "Payment Type",
                field: "paymentType",
                editable: false
           },
           {
                headerName: "Activity",
                field: "activity",
                editable: false
           },
           {
               headerName: "Cr/Dr",
               field: "type",
               editable: false
           },
           {
               field: "expected",
               editable: false
           },
           {
               field: "actual",
               editable: false
           }
         ],
         defaultColDef: {editable: true, sortable: true, filter: true},
         rowSelection: 'multiple',
         animateRows: true
       };

  const eGridDiv = document.getElementById("balanceSheetView");
  new agGrid.Grid(eGridDiv, gridOptions);
  fetch("http://localhost:8080/dinehouse/api/v1/web-ui/balance-sheet")
  .then(response => response.json())
  .then(data => {
     gridOptions.api.sizeColumnsToFit();
     gridOptions.api.setRowData(data);
  });
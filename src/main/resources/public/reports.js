
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

  const tranGridOptions = {
        suppressClickEdit: true,
        suppressHorizontalScroll: true,
        columnDefs: [
             {
                 headerName: "Confirmed By",
                 field: "userId",
                 editable: false
             },
             {
                 headerName: "Tran Group",
                 field: "tranGroup",
                 editable: false
             },
             {
                 headerName: "Dr/Cr",
                 field: "type",
                 editable: false
             },
             {
                  headerName: "Payment Method",
                  field: "paymentMethod",
                  editable: false
             },
             {
                  field: "amount",
                  editable: false
             }
           ],
           defaultColDef: {editable: false, sortable: true, filter: true},
           rowSelection: 'multiple',
           animateRows: true
         };

    const eTranGridDiv = document.getElementById("tranGroupView");
    new agGrid.Grid(eTranGridDiv, tranGridOptions);

     fetch("http://localhost:8080/dinehouse/api/v1/daily-report/trans")
    .then(response => response.json())
    .then(data => {
       tranGridOptions.api.sizeColumnsToFit();
       tranGridOptions.api.setRowData(data);
    });
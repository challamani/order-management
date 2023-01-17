 const gridOptions = {
      columnDefs: [
           {
                headerName: "OrderId",
                field: "id"
           },
           {
               headerName: "Location",
               field: "address"
           },
           {
                headerName: "price",
                field: "payableAmount"
           },
           { field: "type" },
           {
                headerName: "Created By",
                field: "userId"
           },
           {
                field: "status",
                editable: true,
                cellEditor:'agSelectCellEditor',
                cellEditorParams: {
                      values: ['OPEN', 'CANCELLED', 'PENDING', 'DECLINED', 'PREPARING', 'DELIVERED', 'BILL_GENERATED','PAID']
                }
           },
           {
                field: "description",
                editable: true,
                cellEditor: 'agTextCellEditor'
           },
           {
                field: "servedBy"
           },
           { field: "createdOn"}
         ],
         defaultColDef: {sortable: true, filter: true},
         rowSelection: 'multiple',
         animateRows: true
       };

  const eGridDiv = document.getElementById("ordersView");
  new agGrid.Grid(eGridDiv, gridOptions);

  fetch("http://localhost:8080/dinehouse/api/v1/view/orders")
  .then(response => response.json())
  .then(data => {
     gridOptions.api.setRowData(data);
  });
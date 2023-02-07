 const gridOptions = {
      suppressClickEdit: true,
      suppressHorizontalScroll: true,
      columnDefs: [
           {
                headerName: "Payment Type",
                field: "paymentMethod",
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
     //gridOptions.api.sizeColumnsToFit();
     gridOptions.api.setRowData(data);
  });

  function onSendEmail() {
      let text = "Are you sure that you want to send an email to The Dine House email group ? \nEither OK or Cancel.";

      var request = {   recipient: "thedinehouse.in@gmail.com",
                        msgBody: "",
                        subject: "The Dine House - Daily Sales report -"
                    }

      if (confirm(text) == true) {

          fetch('http://localhost:8080/dinehouse/api/v1/send-email', {
          method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request)
          })
          .then(response => response.json())
          .then(response => console.log(JSON.stringify(response)))
          .then(response => location.replace("http://localhost:8080/dinehouse/api/v1/balance-sheet.html"))
      }
   }
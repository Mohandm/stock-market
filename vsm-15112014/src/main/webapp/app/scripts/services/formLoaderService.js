(function () {
var vsmApp = angular.module('vsmApp');


vsmApp.service('FormLoader',['$http', '$routeParams', '$location',function($http, $routeParams, $location){
    var formURL = "";
    var form404 = "/404";
     
  // Here's a simple function to see if our form exists.
  function FormExists(url)
  {
      var http = new XMLHttpRequest();
      http.open('HEAD', url, false);
      http.send();
      return http.status!=404;
  }
   
  function go404() {
      $location.path(form404);
  }
   
 
  this.getTheForm =  function() {
      // Pull information from the $routeParams, 
      // which should be the :group and :form objects.
     var is404 = false; // placeholder for checking whether our form exists
       
      // If both :group and :form are defined, we can see if that form exists.
      if(angular.isDefined($routeParams.formName)) {
           
          // Build our url
           var formName = $routeParams.formName;
          formURL = 'app/views/'+formName+'.html';
           
          // Test to see if it exists. If if does, we're done.
          //console.log("Looking for "+formURL+"..."); 
          if(FormExists(formURL)) {
              return formURL;
          } else {
              // Since :form were set, 
              // we know that if this file doesn't exist then there's no 
              // mistake about it: it's not physically present on the server. 
              go404();
          }
      }
      	else {
              // The only thing that could trigger this 
              // is if the $routeParams.group was undefined but the :form
              // was not. That would be odd indeed. 
              // We'll throw a 404 error here for good measure.
              go404();
          }
          go404();
      }
  }]);
}());
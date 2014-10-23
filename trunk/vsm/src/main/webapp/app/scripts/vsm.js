//Define a function scope, variables used inside it will NOT be globally visible.
(function () {

    var
    message, action,
    //Define the main module.
        vsmApp = angular.module('vsmApp', 
            ['ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch','ui.bootstrap','ui.grid','easypiechart','nvd3ChartDirectives']);

    vsmApp.config(function ($routeProvider, $httpProvider) {
        //configure the rounting of ng-view
       $routeProvider
        .when('/', {
            templateUrl: 'app/views/dashboard.html',
            controller: 'DashboardController'
        })
       .when('/myPortfolio', {
           templateUrl: 'app/views/myPortfolio.html',
           controller: 'MyPortfolioController'
       })
       .when('/charts', {
           templateUrl: 'app/views/charts.html',
           controller: 'ChartsController'
       })
       .when('/watchList', {
           templateUrl: 'app/views/watchList.html',
           controller: 'WatchListController'
       })
       .when('/form/:formName', {
            templateUrl: 'app/views/form.html',
            controller: 'FormSelectorController'
        })
        .when('/action/:actionCode/:value?', {
            templateUrl: 'app/views/result.html',
            controller: 'ActionController'
        })
        .when('/trades', {
            templateUrl: 'app/views/trades.html',
            controller: 'TradesController'
        })
        .otherwise({
            redirectTo: '/'
        });

        // Configure $http to catch authentication error responses
        $httpProvider.interceptors.push(['$injector',function ($injector) {
            return $injector.get('AuthInterceptorService');
        }]);

      //configure $http to catch message responses and show them
        $httpProvider.responseInterceptors.push(function ($q, $timeout) {
            
            var setMessage = function (response) {
                //if the response hvsmApp a text and a type property, it is a message to be shown
                if (response.data && response.data.text && response.data.type) {
                    message = {
                        text: response.data.text,
                        type: response.data.type,
                        show: true
                    };
                    $timeout(function(){message = {};}, 5000);  
                }
            };
            return function (promise) {
                return promise.then(
                    //this is called after each successful server request
                    function (response) {
                        setMessage(response);
                        return response;
                    },
                    //this is called after each unsuccessful server request
                    function (response) {
                        setMessage(response);
                        return $q.reject(response);
                    }
                );
            };
        });
    });
    
    vsmApp.run(function ($rootScope,$http, AlertsService, $location,TourService, AuthService) {
        //make current message accessible to root scope and therefore all scopes
        $rootScope.message = function () {
            return message;
        };

        $rootScope.isAuthenticated = function () {
            return AuthService.isAuthenticated();
        };

        $rootScope.onPageLoad = function(){
            var alertsListPromise = AlertsService.getAlertsList();
            alertsListPromise.then(function(data){
                $rootScope.alertsList = data;
            });
        };

        $rootScope.startTour = function(){
            var steps = TourService.getCurrentPageSetup();

            $rootScope.tour = new Tour({
                steps: steps,
            });
            // Initialize the tour
            $rootScope.tour.init();

            $rootScope.tour.start(true);
        };

        $rootScope.$on("$locationChangeStart", function (event, next, current) {
            var array = ['/','/myPortfolio','/charts','/trades','/watchList'];
            $rootScope.animateTransition = false;
            $(array).each(function(index,item){
                if($location.path() === item)
                {
                    $rootScope.animateTransition = true;
                }
            });
        });
    });
}());
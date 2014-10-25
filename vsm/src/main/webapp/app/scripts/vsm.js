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
           controller: 'MyPortfolioController',
           needsLogin : true
       })
       .when('/leaderBoard', {
           templateUrl: 'app/views/leaderBoard.html',
           controller: 'LeaderBoardController'
       })
       .when('/leagues', {
           templateUrl: 'app/views/leagues.html',
           controller: 'LeaguesController',
           needsLogin : true
       })
       .when('/followers', {
           templateUrl: 'app/views/followers.html',
           controller: 'FollowersController',
           needsLogin : true
       })
        .when('/charts', {
           templateUrl: 'app/views/charts.html',
           controller: 'ChartsController'
       })
        .when('/alerts', {
            templateUrl: 'app/views/alerts.html',
            controller: 'AlertsController'
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
    
    vsmApp.run(function ($rootScope,$http, AlertsService, $location,TourService, AuthService, AUTH_EVENTS) {
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
                storage : false
            });
            // Initialize the tour
            $rootScope.tour.init();

            $rootScope.tour.start(true);
        };

        var setLogoutMessage = function ($timeout) {
            message = {
                text: "You have been successfully logged out.",
                type: "success",
                show: true
            };
        };

        $rootScope.$on(AUTH_EVENTS.logoutSuccess, setLogoutMessage);

        $rootScope.$on("$locationChangeStart", function (event, next, current) {
            var array = ['/','/myPortfolio','/charts','/watchList','/alerts','/leaderBoard','/leagues','/followers'];
            $rootScope.animateTransition = false;
            $(array).each(function(index,item){
                if($location.path() === item)
                {
                    $rootScope.animateTransition = true;
                }
            });
        });

        $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){
            // If user is not logged in try to sync the session and show login modal dialog if required
            if (!AuthService.isAuthenticated()) {
                // Session might still be active on the server side
                AuthService.syncSession().then(function () {
                  // If user is not logged in and the route needs login show the modal dialog
                  if (!AuthService.isAuthenticated() && angular.isDefined(currRoute.needsLogin) && currRoute.needsLogin) {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                }
              });
            }
        });
    });
}());
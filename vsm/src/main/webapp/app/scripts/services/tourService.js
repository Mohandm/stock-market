var vsmApp = angular.module('vsmApp');

vsmApp.service('TourService', ['$http','$rootScope', function ($http, $rootScope) {

    var dashboard = [
        {
            element: "#stock-quote-summary-panel-id",
            title: "NASDAQ 100 Index",
            content: "This section gives you the NASDAQ 100 index. Along with which the Global News from NASDAQ is also shown",
            placement: "bottom"
        },
        {
            element: "#select-symbol-dashboard-id",
            title: "Select Ticker Stock Symbol",
            content: "Select a Ticker Symbol to know more about its Current Standings in the Stock Market.",
            placement: "bottom"
        },
        {
            element: "#current-quote-data-dashboard-id",
            title: "Current Quote Data",
            content: "Below is the current quote data for the selected Symbol.",
            placement: "top"
        },
        {
            element: "#charts-dashboard-id",
            title: "Sparkline Charts",
            content: "These charts give you a quick understanding of how the stock value is changing over time. Read left to right. 2 months of market price is considered",
            placement: "top"
        },
        {
            element: "#historical-data-dashboard-id",
            title: "Historical Data",
            content: "This table gives you the OHLC (Open, High, Low, Close) for a stock in the past. 6 months of Historical Data is available.",
            placement: "top"
        },
        {
            element: "#news-dashboard-id",
            title: "Specific News",
            content: "NASDAQ News specific to Companies is shown here.",
            placement: "top"
        }
    ];

    var chart = [
        {
            element: "#charts-symbols-id",
            title: "Select Ticker Stock Symbol",
            content: "Select a Stock to compare it with other Stocks",
            placement: "bottom"
        },
        {
            element: "#charts-multi-bar-id",
            title: "Multi Bar Chart",
            content: "This chart compares the stocks based on their historical data.",
            placement: "top"
        },
        {
            element: "#charts-stacked-area-id",
            title: "Stacked Area Chart",
            content: "This chart compares the stocks based on their historical data.",
            placement: "top"
        }
    ];

    var leaderBoard = [
        {
            element: "#leagueLeaderBoard",
            title: "Leader Board",
            content: "This is a Ranking Board which lists the Leagues and their corresponding Top 10 Ranked players. " +
                "You can follow these players to get insights of what they are trading to be successful.",
            placement: "top"
        },
        {
            element: "#premierLeagueLeaderBoard",
            title: "Premier League",
            content: "This is the starter league. Play this league and become a champion and you can unlock the next league to play with the " +
                "Champions.",
            placement: "bottom"
        },
        {
            element: "#championsLeagueLeaderBoard",
            title: "Champions League",
            content: "This is the mid league. Play this league and become a champion and you can unlock the next league to play with the " +
                "Legends.",
            placement: "bottom"
        },
        {
            element: "#legendLeagueLeaderBoard",
            title: "Legends League",
            content: "This is the Ultimate league for the Legends of Stock Market.",
            placement: "bottom"
        },
        {
            element: "#viewChampionsLeagueLeaderBoard",
            title: "Champions of League",
            content: "Click here and view Champions corresponding Leagues.",
            placement: "top"
        }
    ];

    var leagues = [
        {
            element: "#leagueLeaguesBoard",
            title: "Leagues",
            content: "Unlock Leagues and view the Players under every League and start following them.",
            placement: "top"
        },
        {
            element: "#premierLeagueLeaguesBoard",
            title: "Premier League",
            content: "This is the starter league. Play this league and become a champion and you can unlock the next league to play with the " +
                "Champions.",
            placement: "bottom"
        },
        {
            element: "#championsLeagueLeaguesBoard",
            title: "Champions League",
            content: "This is the mid league. Play this league and become a champion and you can unlock the next league to play with the " +
                "Legends.",
            placement: "bottom"
        },
        {
            element: "#legendLeagueLeaguesBoard",
            title: "Legends League",
            content: "This is the Ultimate league for the Legends of Stock Market.",
            placement: "bottom"
        },
        {
            element: "#unlockLeagueLeaguesBoard",
            title: "Unlock Leagues",
            content: "Click here and learn how to unlock a League.",
            placement: "top"
        },
        {
            element: "#viewListUsersLeagueLeaguesBoard",
            title: "Players of League",
            content: "Click here and view Players corresponding Leagues.",
            placement: "top"
        }
    ];

    var myPortfolio = [
        {
            element: "#selectLeagueMyPortfolio",
            title: "Select Portfolio (League)",
            content: "Select a portfolio for a league and start trading and increase your Portfolio Value and essentially the Total League Value",
            placement: "bottom"
        },
        {
            element: "#portfolioSummaryMyPortfolio",
            title: "Summary of Portfolio",
            content: "Here you can see your Portfolio Value and Remaining Cash Balance. Sum of which is the Total Value",
            placement: "top"
        },
        {
            element: "#stockHoldingsMyPortfolio",
            title: "Stock Holdings",
            content: "List of all your current Stocks that are in your Portfolio. You can sell them " +
                "by clicking on the 'sell' button for a corresponding stocks",
            placement: "top"
        },
        {
            element: "#stockListsMyPortfolio",
            title: "Current Stock Lists",
            content: "List of all the current stocks available to be bought. Click on 'buy' to make a trade.",
            placement: "top"
        },
        {
            element: "#recentTradesMyPortfolio",
            title: "Recent Trades",
            content: "Your recent trades are listed here according to the date.",
            placement: "top"
        }
    ];

    var watchList = [
        {
            element: "#watchListGrid",
            title: "Watch List",
            content: "Select a stock and start watching the stock for price changes by setting a Higher Limit and Lower Limit. " +
                "When the limit has been reached, you will be notified with a email and also a user notification in the top menu, .",
            placement: "top"
        }
    ];

    var alerts = [
        {
            element: "#alertsGrid",
            title: "Alerts",
            content: "List of all your alerts",
            placement: "top"
        }
    ];

    var followers = [
        {
            element: "#yourFollowersGrid",
            title: "Your Followers",
            content: "This is the list of Players who are following you or whose Follow requests are pending Acceptance or Rejection.",
            placement: "top"
        },
        {
            element: "#yourFollowingGrid",
            title: "Players you are following",
            content: "This is the list of Players whom you are following. You can see the Recent Trades of the corresponding Player by clicking on the 'Recent Trades' button.",
            placement: "top"
        }
    ];

    var tourOptions = {
        'dashboard' : dashboard,
        'chart' : chart,
        'leaderBoard' : leaderBoard,
        'leagues' : leagues,
        'myPortfolio' : myPortfolio,
        'watchList' : watchList,
        'alerts' : alerts,
        'followers' : followers
    };

    var currentPage = 'dashboard';

    this.setupTour = function(page){
        currentPage = page;
    };

    this.getCurrentPageSetup = function(){
        return tourOptions[currentPage];
    };
}]);
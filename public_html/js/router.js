define([
    'backbone',
    'views/viewmanager'
], function(
    Backbone,
    ViewManager
) {
    console.log("before class Router");
    var Router = Backbone.Router.extend({
        routes: {
            'score': 'scoreAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'register': 'registerAction',
            'adminpage' : 'adminAction',
            '': 'defaultActions'
        },

        myManager: null,

        initialize: function () {
            this.myManager = new ViewManager(); 
        },

        showView: function(view) {
            this.myManager.displayView(view);
        },

        defaultActions: function() {
            this.showView(this.myManager.MAIN_VIEW);
        },

        scoreAction: function() {
            this.showView(this.myManager.SCOREBOARD_VIEW);
        },

        gameAction: function() {
            this.showView(this.myManager.GAME_VIEW);
        },

        loginAction: function() {
            this.showView(this.myManager.LOGIN_VIEW);
        },

        registerAction: function() {
            this.showView(this.myManager.REGISTER_VIEW);
        },

        adminAction: function() {
            this.showView(this.myManager.ADMIN_VIEW);
        }


    });
    console.log("after class Router");
    return new Router();
});

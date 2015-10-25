define([
    'backbone',
    'tmpl/scoreboard'
], function(
    Backbone,
    tmpl
){

    return Backbone.View.extend({

        template: tmpl,
        el : 'div#scoreboard',
        initialize: function () {
            // TODO
        },
        render: function () {
            this.$el.html(scoreboardTmpl());
        },
        show: function () {
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }

    });

});

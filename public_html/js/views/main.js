define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
) {

    return Backbone.View.extend({

        template: tmpl,
        el : 'div#main',

        initialize: function() {

        },

        render: function() {
            this.$el.html(mainTmpl());


        },

        show: function() {
          this.$el.show();
          $.ajax({
                  type: "POST",
                  url: "/islogged",
                  data: null,
                  success: function(){
                     $('#logButton').html('<a class="btn btn-default" id="logoutButton">Log out</a>')
                     $('#logoutButton').click(function() {
                          $.ajax({
                              type: "POST",
                              url: "/logout",
                              success: function() {
                                  $('#logButton').html('<a class="btn btn-default" href="#login">Log in</a>')
                              }
                          });
                     });
                  },

                  statusCode: {
                      403: function() {
                          $('#logButton').html('<a class="btn btn-default" href="#login">Log in</a>')
                      }
                  }

              });
        },

        hide: function() {
          this.$el.hide();
        }

    });

});

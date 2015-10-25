define([
    'backbone',
    'tmpl/login'
], function(
    Backbone,
    tmpl
){

    return Backbone.View.extend({

        template: tmpl,

        el: 'div#login',

        initialize: function () {

        },

        render: function () {


            this.$el.html(loginTmpl());


            $("#idForm").on("submit", function(event) {
                event.preventDefault();
                $("#idForm").removeClass('animated shake');
                $.ajax({
                    type: "POST",
                    url: "/signin",
                    data: $(this).serialize(),
                    success: function(){
                        window.location.replace("/#");
                    },
                    statusCode: {
                      403: function(data) {
                            $("#passwordControl").show();
                            $("#loginControl").show();
                            $("#loginGroup").removeClass( "form-group has-error" ).addClass( "form-group" );
                            $("#passwordGroup").removeClass( "form-group has-error" ).addClass( "form-group" );

                            $("#idForm").addClass('animated shake');

                            switch(data.getResponseHeader('Error')){
                                case '0':
                                    $("#passwordControl").html('<label class="control-label" for="password1" id="passwordLog">Incorrect password</label>')
                                    $("#passwordGroup").removeClass( "form-group" ).addClass( "form-group has-error" );
                                    $("#loginControl").hide();
                                    break
                                case '1':
                                    $("#loginControl").html('<label class="control-label" for="login1" id="passwordLog">User does not exist</label>')
                                    $("#loginGroup").removeClass( "form-group" ).addClass( "form-group has-error" );
                                    $("#passwordControl").hide();
                                    break
                                default:
                                    break
                            }
                        }
                    },
                });
            });


        },
        show: function () {
            this.$el.show();
            $.ajax({
                    type: "POST",
                    url: "/islogged",
                    data: null,
                    success: function(){
                       window.location.replace("/#");
                    },
            });
        },
        hide: function () {
            this.$el.hide();
        },

    });

});

define([
    'backbone',
    'tmpl/register'
], function(
    Backbone,
    tmpl
) {

    return Backbone.View.extend({

            template: tmpl,
            el: 'div#register',

            initialize: function() {
                // TODO
            },
            render: function() {


                this.$el.html(registerTmpl());


                $("#registerForm").on("submit", function(event) {
                        event.preventDefault();

                        $("#password1Group").removeClass("form-group has-error").addClass("form-group");
                        $("#password2Group").removeClass("form-group has-error").addClass("form-group");
                        $("#passwordNotification").html('');

                        var aaa = $("#password1").val();
                        var aa = $("#password2").val();
                        if (aaa == aa) {

                            $.ajax({
                                    type: "POST",
                                    url: "/signup",
                                    data: $(this).serialize(),
                                    success: function(data) {
                                        window.location.replace("/#login");
                                    },
                                    statusCode: {
                                        403: function(data) {
                                        $("#emailNotification").html('<label class="control-label" for="password1" id="passwordLog">Sorry, this email is already engaged</label>')
                                        $("#emailGroup").removeClass("form-group").addClass("form-group has-error");

                                    }
                                }
                            });
                    } else {
                        $("#passwordNotification").html('<label class="control-label" for="inputWarning2">Passwords does not match</label>')
                        $("#password1Group").addClass("has-error");
                        $("#password2Group").addClass("has-error");
                    }


                });
        },
        show: function() {
            this.$el.show();
            $.ajax({
                type: "POST",
                url: "/islogged",
                data: null,
                success: function() {
                    window.location.replace("/#");
                },
            });
        },
        hide: function() {
            this.$el.hide();
        }

    });

});

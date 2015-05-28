package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    public static Result createAD() { return redirect(""); }
    public static Result sobre() { return ok(sobre.render()); }
    public static Result anuncios() { return ok(anuncios.render());}
}

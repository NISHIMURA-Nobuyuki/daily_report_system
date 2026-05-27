package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import actions.ActionBase;
import actions.UnknownAction;
import constants.ForwardConst;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        ActionBase action = getAction(request, response);
        
        action.init(getServletContext(), request, response);
        
        action.process();
        
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    private ActionBase getAction(HttpServletRequest request, HttpServletResponse response) {
        Class type = null;
        ActionBase action = null;
        try {
            
            String actionString = request.getParameter(ForwardConst.ACT.getValue());
            
            type = Class.forName(String.format("actions.%sAction", actionString));
            
            action = (ActionBase)(type.asSubclass(ActionBase.class)
                    .getDeclaredConstructor()
                    .newInstance());
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException
                | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            action = new UnknownAction();
        }
        return action;
    }
    
    
}

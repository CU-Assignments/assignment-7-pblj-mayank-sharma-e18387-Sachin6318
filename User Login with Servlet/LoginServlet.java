import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simple static validation
        if("admin".equals(username) && "password123".equals(password)) {
            request.setAttribute("user", username);
            RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
            dispatcher.forward(request, response);
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<h3>Login Failed. Invalid credentials!</h3>");
            response.getWriter().println("<a href='login.html'>Try Again</a>");
        }
    }
}

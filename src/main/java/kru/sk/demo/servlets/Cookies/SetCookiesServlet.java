package kru.sk.demo.servlets.Cookies;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SetCookiesServlet")
public class SetCookiesServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Cookie cookie1 = new Cookie("some_id", "123");
    Cookie cookie2 = new Cookie("some_name", "Bob");

    cookie1.setMaxAge(24 * 60 * 60);
    cookie2.setMaxAge(24 * 60 * 60);

    resp.addCookie(cookie1);
    resp.addCookie(cookie2);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }
}

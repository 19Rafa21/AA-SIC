package backend.Controllers;

import backend.DTOs.Reply.RegisterReplyDTO;
import backend.DTOs.Reply.ReplyDTO;
import backend.DTOs.Reply.UpdateReplyDTO;
import backend.DTOs.UserDTO;
import backend.Models.Reply;
import backend.Utils.HttpRequestUtils;
import com.google.gson.Gson;
import backend.Services.ReplyService;
import backend.Services.UserService;
import com.google.gson.JsonSyntaxException;
import org.orm.PersistentException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ReplyController extends HttpServlet {

    private ReplyService replyService;
    private UserService userService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.replyService = new ReplyService();
        this.userService = new UserService();
        this.gson = new Gson();
    }

    // GET: /api/replies/review/{reviewId}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Reply> replies = replyService.getAllReplies();
                List<ReplyDTO> dtos = replies.stream()
                        .map(ReplyDTO::new)
                        .toList();
                response.getWriter().println(gson.toJson(dtos));
            } else {
                String[] parts = pathInfo.split("/");
                if (parts.length >= 2) {
                    String replyId = parts[1];

                    Reply reply = replyService.getReplyById(replyId);
                    if (reply == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reply não encontrada.");
                        return;
                    }

                    if (parts.length == 2) {
                        ReplyDTO replyDTO = new ReplyDTO(reply);
                        response.getWriter().println(gson.toJson(replyDTO));
                    } else if (parts.length == 3) {
                        String subResource = parts[2];

                        if ("author".equals(subResource)) {
                            UserDTO userDTO = new UserDTO(reply.getAuthor());
                            response.getWriter().println(gson.toJson(userDTO));
                        } else {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "sub-recurso não encontrado.");
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de URL inválido.");
                }
            }
        } catch (Exception e) {
	        e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RegisterReplyDTO replyDTO = gson.fromJson(HttpRequestUtils.readBodyJson(request), RegisterReplyDTO.class);

            boolean saved = replyService.registerReply(replyDTO);
            if (saved) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().println("{\"status\": \"reply registada com sucesso\"}");
            }

        } catch (JsonSyntaxException e) {
	        throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }

    // PUT: /api/replies/{replyId}
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da reply é obrigatório na URL.");
            return;
        }

        String id = pathInfo.substring(1);

        try {
            UpdateReplyDTO updateDTO = gson.fromJson(HttpRequestUtils.readBodyJson(request), UpdateReplyDTO.class);

            if (updateDTO.getText() == null || updateDTO.getText().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nenhum campo fornecido para atualizar.");
                return;
            }

            replyService.updateReply(id, updateDTO);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().println("{\"status\": \"Reply atualizada com sucesso.\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }

    // DELETE: /api/replies/{replyId}
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da review não especificado.");
                return;
            }

            String replyId = pathInfo.split("/")[1];

            Reply reply = replyService.getReplyById(replyId);
            if (reply == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reply não encontrada.");
                return;
            }

            boolean deleted = replyService.deleteReply(reply);
            if (deleted) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().println("{\"status\": \"reply removida com sucesso\"}");
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().println("{\"status\": \"Erro ao remover a reply\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }
}

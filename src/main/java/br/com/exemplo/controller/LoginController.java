package online.suacasa.controller;

import javax.validation.Valid;
import online.suacasa.model.Usuario;
import online.suacasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuario = new Usuario();
        modelAndView.addObject("usuario", usuario);
        modelAndView.setViewName("cadastro");
        return modelAndView;
    }

    @PostMapping("/cadastro")
    public ModelAndView createNewUser(@Valid Usuario usuario, BindingResult bindingResult) {
        
        ModelAndView modelAndView = new ModelAndView();
        
        Usuario userExists = userService.findUserByEmail(usuario.getEmail());

        if (userExists != null) {
            bindingResult.rejectValue("email", "EmailExists", "Já existe um usuário cadastrado com esse e-mail.");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("usuario", usuario);
            modelAndView.setViewName("cadastro");
        } else {
            userService.saveUser(usuario);
            modelAndView.addObject("successMessage", "Usuário cadastrado com sucesso!");
            modelAndView.addObject("usuario", new Usuario());
            modelAndView.setViewName("cadastro");
        }

        return modelAndView;
    }

    @GetMapping("/admin/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Bem vindo, " + user.getNome() + " " + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Conteúdo disponível apenas para usuários ADMIN.");
        modelAndView.setViewName("home");
        return modelAndView;
    }

}

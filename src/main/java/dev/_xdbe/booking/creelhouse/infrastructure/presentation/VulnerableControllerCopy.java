package dev._xdbe.booking.creelhouse.infrastructure.presentation;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
public class VulnerableControllerCopy {

    @GetMapping("/copy")
    public String copy(HttpServletRequest request) {
        return request.getParameter("input"); // vulnerable pattern for Semgrep
    }
}

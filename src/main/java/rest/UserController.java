package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method= RequestMethod.GET, value="/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, value="/users")
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.GET, value="/users/{id}")
    public User getUserById(@PathVariable("id") String id, HttpServletResponse httpResponse) {
        if (!userRepository.exists(id)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        } else {
            return userRepository.findUserById(id);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
    public User editUser(@RequestBody User user, @PathVariable("id") String id, HttpServletResponse httpResponse) {
        if (!userRepository.exists(id)) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        } else {
            user.setId(id);
            return userRepository.save(user);
        }
    }
}

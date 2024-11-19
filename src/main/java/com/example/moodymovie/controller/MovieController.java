//package com.example.moodymovie.controller;
//
//import com.example.moodymovie.controller.request.UserRequest;
//import com.example.moodymovie.controller.response.SingleMovieResponse;
//import com.example.moodymovie.service.MovieService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/form")
//@SessionAttributes("userRequest")
//public class MovieController {
//
//    private final MovieService movieService;
//
//    @ModelAttribute("userRequest")
//    public UserRequest userRequest() {
//        return new UserRequest();
//    }
//
//    @GetMapping("/page/{pageNumber}")
//    public String showFormPage(@PathVariable int pageNumber, Model model) {
//        return "form-page" + pageNumber;
//    }
//
//    @PostMapping("/page/{pageNumber}")
//    public String processFormPage(@PathVariable int pageNumber, @ModelAttribute UserRequest userRequest, Model model) {
//        if (pageNumber < 5) {
//            return "redirect:/form/page/" + (pageNumber + 1);
//        } else {
//            ResponseEntity<String> response = getSyncMovie(userRequest);
//            if (response.getStatusCode() == HttpStatus.OK) {
//                Page<SingleMovieResponse> moviePage = getSingleMoviePaginated(userRequest.getPage(), 1).getBody();
//                model.addAttribute("moviePage", moviePage);
//            }
//            return "form-success";
//        }
//    }
//
//    @GetMapping("/success")
//    public String showSuccessPage() {
//        return "form-success";
//    }
//
//    @PostMapping
//    @RequestMapping("/sync-movie")
//    public ResponseEntity<String> getSyncMovie(@RequestBody UserRequest userRequest) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(movieService.filterAndSaveMovie(userRequest));
//    }
//
//    @GetMapping("/get-movie")
//    public ResponseEntity<Page<SingleMovieResponse>> getSingleMoviePaginated(@RequestParam(defaultValue = "0") int page,
//                                                                             @RequestParam(defaultValue = "1") int size) {
////        Page<SingleMovieResponse> movies = movieService.getSingleMoviePaginated(pageable);
////        return new ResponseEntity<>(movies, HttpStatus.OK);
//        Page<SingleMovieResponse> movies = movieService.getSingleMoviePaginated(PageRequest.of(page, size));
//        return new ResponseEntity<>(movies, HttpStatus.OK);
//    }
//}


package com.example.moodymovie.controller;

import com.example.moodymovie.controller.request.UserRequest;
import com.example.moodymovie.controller.response.SingleMovieResponse;
import com.example.moodymovie.service.MovieService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/form")
@SessionAttributes("userRequest")
public class MovieController {

    private final MovieService movieService;

    @ModelAttribute("userRequest")
    public UserRequest userRequest() {
        return new UserRequest();
    }

    @GetMapping("/page/{pageNumber}")
    public String showFormPage(@PathVariable int pageNumber, Model model, HttpSession session) {
        if (!isPageAccessible(pageNumber, session)) {
            return "redirect:/form/page/" + getLastCompletedPage(session);
        }
        return "form-page" + pageNumber;
    }

    @PostMapping("/page/{pageNumber}")
    public String processFormPage(@PathVariable int pageNumber, @ModelAttribute UserRequest userRequest, Model model, HttpSession session) {
        session.setAttribute("formPage" + pageNumber + "Completed", true);
        session.setAttribute("userRequest", userRequest);

        if (pageNumber < 5) {
            return "redirect:/form/page/" + (pageNumber + 1);
        } else {
            ResponseEntity<String> response = getSyncMovie(userRequest);
            if (response.getStatusCode() == HttpStatus.OK) {
                Page<SingleMovieResponse> moviePage = getSingleMoviePaginated(userRequest.getPage(), 1).getBody();
                model.addAttribute("moviePage", moviePage);
            }
            return "form-success";
        }
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "form-success";
    }

    @PostMapping("/sync-movie")
    public ResponseEntity<String> getSyncMovie(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(movieService.filterAndSaveMovie(userRequest));
    }

    @GetMapping("/get-movie")
    public ResponseEntity<Page<SingleMovieResponse>> getSingleMoviePaginated(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "1") int size) {
        Page<SingleMovieResponse> movies = movieService.getSingleMoviePaginated(PageRequest.of(page, size));
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    private boolean isPageAccessible(int pageNumber, HttpSession session) {
        for (int i = 1; i < pageNumber; i++) {
            if (!Boolean.TRUE.equals(session.getAttribute("formPage" + i + "Completed"))) {
                return false;
            }
        }
        return true;
    }

    private int getLastCompletedPage(HttpSession session) {
        for (int i = 5; i > 0; i--) {
            if (Boolean.TRUE.equals(session.getAttribute("formPage" + i + "Completed"))) {
                return i + 1;
            }
        }
        return 1;
    }
}

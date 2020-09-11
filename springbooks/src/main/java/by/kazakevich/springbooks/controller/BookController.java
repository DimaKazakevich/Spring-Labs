package by.kazakevich.springbooks.controller;

import by.kazakevich.springbooks.forms.BookForm;
import by.kazakevich.springbooks.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class BookController {
    private static List<Book> books = new ArrayList<>();

    static {
        books.add(new Book("Full Stack Development with JHipster", "Deepu K Sasidharan, Sendil Kumar N"));
        books.add(new Book("Pro Spring Security", "Carlo Scarioni, Massimo Nardone"));
    }

    //inject from application.properties
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @GetMapping({"/", "/index"})
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", this.message);
        return modelAndView;
    }

    @GetMapping({"/allbooks"})
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booklist");
        model.addAttribute("books", books);
        log.info("/allbooks was called");
        return modelAndView;
    }

    @GetMapping({"/addbook"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addbook");
        BookForm bookForm = new BookForm();
        model.addAttribute("bookform", bookForm);
        log.info("/addbook(GET) was called");
        return modelAndView;
    }

    @PostMapping({"/addbook"})
    public ModelAndView savePerson(Model model, @ModelAttribute("bookform") BookForm bookForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booklist");
        String title = bookForm.getTitle();
        String author = bookForm.getAuthor();
        if (title != null && !title.isEmpty() && author != null && !author.isEmpty()) {
            Book newBook = new Book(title, author);
            books.add(newBook);
            model.addAttribute("books", books);
            return new ModelAndView("redirect:/allbooks");
        }
        model.addAttribute("errorMessage", this.errorMessage);
        modelAndView.setViewName("addbook");
        log.info("/addbook(POST) was called");
        return modelAndView;
    }

    @DeleteMapping({"/deletebook"})
    public ModelAndView deleteBook(String selectedTableRow) {
        for(int i = 0; i < books.size(); i++) {
            if ((books.get(i)).getAuthor().equals(selectedTableRow)) {
                books.remove(books.get(i));
            }

            if ((books.get(i)).getTitle().equals(selectedTableRow)) {
                books.remove(books.get(i));
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booklist");
        log.info("/deletebook was called");
        return modelAndView;
    }

    @PutMapping({"/edit"})
    public ModelAndView editBookInfo(String beforeChanges, String changes) {
        for (Book book: books) {
            if (book.getAuthor().equals(beforeChanges)) {
                book.setAuthor(changes);
            }

            if (book.getTitle().equals(beforeChanges)) {
                book.setTitle(changes);
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booklist");
        log.info("/edit was called");
        return modelAndView;
    }
}

package io.shinde.specimensearch.controller;

import io.shinde.specimensearch.model.Specimen;
import io.shinde.specimensearch.service.SpecimenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SpecimenController {

    private final SpecimenService service;

    public SpecimenController(SpecimenService service) {
        this.service = service;
    }

    @GetMapping("/specimens")
    public List<Specimen> all() {
        return service.findAll();
    }

    @GetMapping("/specimens/{id}")
    public ResponseEntity<Specimen> byId(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/specimens/search")
    public List<Specimen> search(
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "taxon", required = false) String taxon) {
        return service.search(q, taxon);
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of("status", "ok", "indexed", service.indexSize());
    }
}

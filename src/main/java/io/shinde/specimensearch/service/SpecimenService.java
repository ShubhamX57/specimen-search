package io.shinde.specimensearch.service;

import io.shinde.specimensearch.model.Specimen;
import io.shinde.specimensearch.repository.SpecimenRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SpecimenService {

    private final SpecimenRepository repository;

    public SpecimenService(SpecimenRepository repository) {
        this.repository = repository;
    }

    public List<Specimen> findAll() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Specimen::id))
                .toList();
    }

    public Optional<Specimen> findById(String id) {
        return repository.findById(id);
    }

    public List<Specimen> search(String query, String taxon) {
        String needle = query == null ? "" : query.toLowerCase().trim();
        return repository.findAll().stream()
                .filter(s -> needle.isEmpty() || s.searchableText().contains(needle))
                .filter(s -> taxon == null || taxon.isBlank()
                        || s.taxon().equalsIgnoreCase(taxon))
                .sorted(Comparator.comparing(Specimen::id))
                .toList();
    }

    public int indexSize() {
        return repository.size();
    }
}

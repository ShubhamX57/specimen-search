package io.shinde.specimensearch.repository;

import io.shinde.specimensearch.model.Specimen;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SpecimenRepository {

    private final Map<String, Specimen> index = new ConcurrentHashMap<>();

    public SpecimenRepository() {
        seed();
    }

    private void seed() {
        save(new Specimen("SPC-001", "Mouse liver section", "Mammalia", "liver",
                "histology fixed paraffin"));
        save(new Specimen("SPC-002", "Zebrafish embryo", "Actinopterygii", "whole",
                "developmental imaging confocal"));
        save(new Specimen("SPC-003", "Human kidney biopsy", "Mammalia", "kidney",
                "clinical frozen pathology"));
        save(new Specimen("SPC-004", "Arabidopsis leaf", "Magnoliopsida", "leaf",
                "plant chlorophyll fluorescence"));
        save(new Specimen("SPC-005", "Drosophila brain", "Insecta", "brain",
                "neuroscience connectome imaging"));
    }

    public void save(Specimen specimen) {
        index.put(specimen.id(), specimen);
    }

    public Optional<Specimen> findById(String id) {
        return Optional.ofNullable(index.get(id));
    }

    public List<Specimen> findAll() {
        return List.copyOf(index.values());
    }

    public int size() {
        return index.size();
    }
}

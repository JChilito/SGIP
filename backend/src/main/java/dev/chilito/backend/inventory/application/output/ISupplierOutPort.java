package dev.chilito.backend.inventory.application.output;

import java.util.List;
import java.util.Optional;

import dev.chilito.backend.inventory.domain.models.Supplier;

public interface ISupplierOutPort {
    Supplier createSupplier(Supplier supplier);

    Supplier updateSupplier(Supplier supplier, Long id);

    void deleteSupplier(Long id);

    Optional<Supplier> getSupplierById(Long id);

    List<Supplier> getAllSuppliers();
}

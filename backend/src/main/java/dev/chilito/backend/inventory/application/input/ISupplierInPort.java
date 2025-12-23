package dev.chilito.backend.inventory.application.input;

import java.util.List;

import dev.chilito.backend.inventory.domain.models.Supplier;

public interface ISupplierInPort {
    Supplier createSupplier(Supplier supplier);

    Supplier updateSupplier(Supplier supplier, Long id);

    void deleteSupplier(Long id);

    Supplier getSupplierById(Long id);

    List<Supplier> getAllSuppliers();
}

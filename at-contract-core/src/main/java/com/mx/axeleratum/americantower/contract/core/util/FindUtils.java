package com.mx.axeleratum.americantower.contract.core.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class FindUtils {
    public static <T> T findByProperty(Collection<T> col, Predicate<T> filter) {
        return col.stream().filter(filter).findFirst().orElse(null);
    }

	public static <T> List<T> findAllByProperty(Collection<T> col, Predicate<T> filter) {
		List<T> c =  col.stream().filter(filter).collect(Collectors.toList());
		return c;
	}
}
package pl.coursera.mszarlinski.symptoms.rest.converter;

import java.util.Collection;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

/**
 * 
 * @author Maciej
 */
public abstract class Converter<E, R> {
	public abstract E convertToEntity(final R resource, final Map<String, Object> params);

	public abstract R convertToResource(final E entity, final Map<String, Object> params);

	public Collection<E> convertToEntities(final Collection<? extends R> objects, final Map<String, Object> params) {
		return Lists.newArrayList(FluentIterable.from(objects).transform(new Function<R, E>() {
			@Override
			public E apply(R arg) {
				return convertToEntity(arg, params);
			}
		}));
	}
 
	public Collection<R> convertToResources(final Collection<? extends E> objects, final Map<String, Object> params) {
		return Lists.newArrayList(FluentIterable.from(objects).transform(new Function<E, R>() {
			@Override
			public R apply(E arg) {
				return convertToResource(arg, params);
			}
		}));
	}
}

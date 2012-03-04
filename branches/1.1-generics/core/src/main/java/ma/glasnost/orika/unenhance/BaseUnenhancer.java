/*
 * Orika - simpler, better and faster Java bean mapping
 * 
 * Copyright (C) 2011 Orika authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ma.glasnost.orika.unenhance;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import ma.glasnost.orika.inheritance.SuperTypeResolver;
import ma.glasnost.orika.inheritance.SuperTypeResolverStrategy;
import ma.glasnost.orika.metadata.Type;

/**
 * Provides a delegating unenhance strategy which also post-processes the
 * unenhancement results using the associated super-type strategies.<br>
 * 
 * See also: {@link #SuperTypeResolverStrategy}  
 * 
 * @author matt.deboer@gmail.com
 */
public class BaseUnenhancer implements UnenhanceStrategy {
    
	private final ConcurrentHashMap<Type<?>,Type<?>> mappedSuperTypes;
	private final Queue<UnenhanceStrategy> unenhanceStrategyChain = new LinkedBlockingQueue<UnenhanceStrategy>();
	private final Queue<SuperTypeResolverStrategy> supertypeStrategyChain = new LinkedBlockingQueue<SuperTypeResolverStrategy>();
	
	public BaseUnenhancer() {
		this.mappedSuperTypes = new ConcurrentHashMap<Type<?>,Type<?>>();
	}

	public void addUnenhanceStrategy(final UnenhanceStrategy strategy) {
		unenhanceStrategyChain.add(strategy);
	}
	
	public void addSuperTypeResolverStrategy(final SuperTypeResolverStrategy strategy) {
		supertypeStrategyChain.add(strategy);
	}
	
    public <T> T unenhanceObject(T object) {
        
    	T unenhancedObject = object;
    	for(UnenhanceStrategy strategy: unenhanceStrategyChain) {
    		T delegateUnenhanced = (T) strategy.unenhanceObject(object);
    		// Accept the first delegate strategy result which produces 
    		// something different than the object itself
    		if (delegateUnenhanced != null && delegateUnenhanced != object) {
    			unenhancedObject = delegateUnenhanced;
    			break;
    		}
    	}
    	
    	return unenhancedObject;
    }
    
    @SuppressWarnings("unchecked")
    public <T> Type<T> unenhanceClass(T object) {
        
    	Type<T> unenhancedClass = Type.typeOf(object);
    	for(UnenhanceStrategy strategy: unenhanceStrategyChain) {
    		Type<T> delegateUnenhanced = strategy.unenhanceClass(object);
    		// Accept the first delegate strategy result which produces 
    		// something different than the object's getClass method
    		if (delegateUnenhanced!=null && !unenhancedClass.equals(delegateUnenhanced)) {
    			unenhancedClass = delegateUnenhanced;
    			break;
    		}
    	}
    	
    	for(SuperTypeResolverStrategy strategy: supertypeStrategyChain) {
    		Type<?> superType = SuperTypeResolver.getSuperType(unenhancedClass,strategy);
        	if (superType!=null && !unenhancedClass.equals(superType)) {
        		Type<?> superTypePutResult = mappedSuperTypes.putIfAbsent(unenhancedClass, superType);
        		// Accept the first delegate strategy result which produces 
        		// a super-type different than the object's getClass method
        		if (superTypePutResult!=null) {
        			superType = superTypePutResult;
        		}
        		unenhancedClass = (Type<T>)superType;
        		break;
        	}
    	}
    	
    	return unenhancedClass;	
    }
}

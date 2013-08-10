/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the MIT License (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blankstyle.vertx.php.file;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.file.FileSystemProps;

import com.blankstyle.vertx.php.AsyncResultHandler;
import com.blankstyle.vertx.php.AsyncResultWrapper;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.buffer.Buffer;
import com.blankstyle.vertx.php.util.HandlerFactory;
import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x FileSystem.
 * 
 * @author Jordan Halterman
 */
public final class FileSystem {

  private org.vertx.java.core.file.FileSystem fileSystem;

  public static final int OPEN_READ = 1;

  public static final int OPEN_WRITE = 2;

  public static final int CREATE_NEW = 4;

  public FileSystem(org.vertx.java.core.file.FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  /**
   * Executes an asynchronous chmod call.
   */
  public FileSystem chmod(Env env, StringValue path, StringValue perms, @Optional StringValue dirPerms, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::chmod() must be callable.");
    if (PhpTypes.notNull(dirPerms)) {
      fileSystem.chmod(path.toString(), perms.toString(), dirPerms.toString(),
          HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    else {
      fileSystem
          .chmod(path.toString(), perms.toString(), HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous chmod call.
   */
  public FileSystem chmodSync(Env env, StringValue path, StringValue perms, @Optional StringValue dirPerms) {
    if (PhpTypes.notNull(dirPerms)) {
      fileSystem.chmodSync(path.toString(), perms.toString(), dirPerms.toString());
    }
    else {
      fileSystem.chmodSync(path.toString(), perms.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous copy call.
   */
  public FileSystem copy(Env env, StringValue from, StringValue to, @Optional BooleanValue recursive, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::copy() must be callable.");
    if (PhpTypes.notNull(recursive)) {
      fileSystem.copy(from.toString(), to.toString(), recursive.toBoolean(),
          HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    else {
      fileSystem.copy(from.toString(), to.toString(), HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous copy call.
   */
  public FileSystem copySync(Env env, StringValue from, StringValue to, @Optional BooleanValue recursive) {
    if (PhpTypes.notNull(recursive)) {
      fileSystem.copySync(from.toString(), to.toString(), recursive.toBoolean());
    }
    else {
      fileSystem.copySync(from.toString(), to.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous create file call.
   */
  public FileSystem createFile(Env env, StringValue path, @Optional StringValue perms, Value handler) {
    PhpTypes
        .assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::createFile() must be callable.");
    if (PhpTypes.notNull(perms)) {
      fileSystem.createFile(path.toString(), perms.toString(),
          HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    else {
      fileSystem.createFile(path.toString(), HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous create file call.
   */
  public FileSystem createFileSync(Env env, StringValue path, @Optional StringValue perms) {
    if (PhpTypes.notNull(perms)) {
      fileSystem.createFileSync(path.toString(), perms.toString());
    }
    else {
      fileSystem.createFileSync(path.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous delete call.
   */
  public FileSystem delete(Env env, StringValue path, @Optional BooleanValue recursive, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::delete() must be callable.");
    if (PhpTypes.notNull(recursive)) {
      fileSystem.delete(path.toString(), recursive.toBoolean(),
          HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    else {
      fileSystem.delete(path.toString(), HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous delete call.
   */
  public FileSystem deleteSync(Env env, StringValue path, @Optional BooleanValue recursive) {
    if (PhpTypes.notNull(recursive)) {
      fileSystem.deleteSync(path.toString(), recursive.toBoolean());
    }
    else {
      fileSystem.deleteSync(path.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous exists call.
   */
  public FileSystem exists(Env env, StringValue path, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::exists() must be callable.");
    fileSystem.exists(path.toString(), new Handler<AsyncResult<Boolean>>(env, PhpTypes.toCallable(handler)));
    return this;
  }

  /**
   * Executes a synchronous exists call.
   */
  public boolean existsSync(Env env, StringValue path) {
    return fileSystem.existsSync(path.toString());
  }

  /**
   * Executes an asynchronous fsprops call.
   */
  public FileSystem fsProps(Env env, StringValue path, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::fsProps() must be callable.");
    fileSystem.fsProps(path.toString(), new Handler<AsyncResult<FileSystemProps>>(env, PhpTypes.toCallable(handler)));
    return this;
  }

  /**
   * Executes a synchronous fsprops call.
   */
  public FileSystemProps fsPropsSync(Env env, StringValue path) {
    return fileSystem.fsPropsSync(path.toString());
  }

  /**
   * Executes an asynchronous link call.
   */
  public FileSystem link(Env env, StringValue link, StringValue existing, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::link() must be callable.");
    fileSystem
        .link(link.toString(), existing.toString(), HandlerFactory.createAsyncVoidHandler(env, handler));
    return this;
  }

  /**
   * Executes a synchronous link call.
   */
  public FileSystem linkSync(Env env, StringValue link, StringValue existing) {
    fileSystem.linkSync(link.toString(), existing.toString());
    return this;
  }

  /**
   * Executes an asynchronous lprops call.
   */
  public FileSystem lprops(Env env, StringValue path, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::lprops() must be callable.");
    fileSystem.lprops(path.toString(), new AsyncResultHandler<org.vertx.java.core.file.FileProps>(env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.file.FileProps, FileProps>() {
      @Override
      public FileProps wrap(org.vertx.java.core.file.FileProps props) {
        return new FileProps(props);
      }
    }));
    return this;
  }

  /**
   * Executes a synchronous lprops call.
   */
  public FileSystem lpropsSync(Env env, StringValue path) {
    fileSystem.lpropsSync(path.toString());
    return this;
  }

  /**
   * Executes an asynchronous mkdir call.
   */
  public FileSystem mkdir(Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue createParents,
      Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::mkdir() must be callable.");
    if (PhpTypes.notNull(perms)) {
      if (PhpTypes.notNull(createParents)) {
        fileSystem.mkdir(path.toString(), perms.toString(), createParents.toBoolean(), HandlerFactory.createAsyncVoidHandler(env, handler));
      }
      else {
        fileSystem.mkdir(path.toString(), perms.toString(),
            HandlerFactory.createAsyncVoidHandler(env, handler));
      }
    }
    else if (PhpTypes.notNull(createParents)) {
      fileSystem.mkdir(path.toString(), createParents.toBoolean(),
          HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    else {
      fileSystem.mkdir(path.toString(), HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    return this;
  }

  /**
   * Executes a synchronous mkdir call.
   */
  public FileSystem mkdirSync(Env env, StringValue path, @Optional StringValue perms,
      @Optional BooleanValue createParents) {
    if (PhpTypes.notNull(perms)) {
      if (PhpTypes.notNull(createParents)) {
        fileSystem.mkdirSync(path.toString(), perms.toString(), createParents.toBoolean());
      }
      else {
        fileSystem.mkdirSync(path.toString(), perms.toString());
      }
    }
    else if (PhpTypes.notNull(createParents)) {
      fileSystem.mkdirSync(path.toString(), createParents.toBoolean());
    }
    else {
      fileSystem.mkdirSync(path.toString());
    }
    return this;
  }

  /**
   * Executes an asynchronous move call.
   */
  public FileSystem move(Env env, StringValue from, StringValue to, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::move() must be callable.");
    fileSystem.move(from.toString(), to.toString(), HandlerFactory.createAsyncVoidHandler(env, handler));
    return this;
  }

  /**
   * Executes a synchronous move call.
   */
  public FileSystem moveSync(Env env, StringValue from, StringValue to) {
    fileSystem.moveSync(from.toString(), to.toString());
    return this;
  }

  /**
   * Executes an asynchronous open call.
   */
  public FileSystem open(Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue read,
      @Optional BooleanValue write, @Optional BooleanValue createNew, @Optional BooleanValue flush, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::open() must be callable.");
    if (PhpTypes.notNull(perms) && PhpTypes.notNull(read) && PhpTypes.notNull(write) && PhpTypes.notNull(createNew)
        && PhpTypes.notNull(flush)) {
      fileSystem.open(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean(),
          flush.toBoolean(),
          new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>(env, PhpTypes.toCallable(handler),
              new AsyncResultWrapper<org.vertx.java.core.file.AsyncFile, AsyncFile>() {
                @Override
                public AsyncFile wrap(org.vertx.java.core.file.AsyncFile file) {
                  return new AsyncFile(file);
                }
              }));
    }
    else if (PhpTypes.notNull(perms) && PhpTypes.notNull(read) && PhpTypes.notNull(write)
        && PhpTypes.notNull(createNew)) {
      fileSystem.open(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean(),
          new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>(env, PhpTypes.toCallable(handler),
              new AsyncResultWrapper<org.vertx.java.core.file.AsyncFile, AsyncFile>() {
                @Override
                public AsyncFile wrap(org.vertx.java.core.file.AsyncFile file) {
                  return new AsyncFile(file);
                }
              }));
    }
    else if (PhpTypes.notNull(perms) && PhpTypes.notNull(createNew)) {
      fileSystem.open(path.toString(), perms.toString(), createNew.toBoolean(),
          new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>(env, PhpTypes.toCallable(handler),
              new AsyncResultWrapper<org.vertx.java.core.file.AsyncFile, AsyncFile>() {
                @Override
                public AsyncFile wrap(org.vertx.java.core.file.AsyncFile file) {
                  return new AsyncFile(file);
                }
              }));
    }
    else if (PhpTypes.notNull(perms)) {
      fileSystem.open(path.toString(), perms.toString(), new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>(
          env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.file.AsyncFile, AsyncFile>() {
            @Override
            public AsyncFile wrap(org.vertx.java.core.file.AsyncFile file) {
              return new AsyncFile(file);
            }
          }));
    }
    else {
      fileSystem.open(path.toString(),
          new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>(env, PhpTypes.toCallable(handler),
              new AsyncResultWrapper<org.vertx.java.core.file.AsyncFile, AsyncFile>() {
                @Override
                public AsyncFile wrap(org.vertx.java.core.file.AsyncFile file) {
                  return new AsyncFile(file);
                }
              }));
    }
    return this;
  }

  /**
   * Executes a synchronous open call.
   */
  public org.vertx.java.core.file.AsyncFile openSync(Env env, StringValue path, @Optional StringValue perms,
      @Optional BooleanValue read, @Optional BooleanValue write, @Optional BooleanValue createNew,
      @Optional BooleanValue flush) {
    if (PhpTypes.notNull(perms) && PhpTypes.notNull(read) && PhpTypes.notNull(write) && PhpTypes.notNull(createNew)
        && PhpTypes.notNull(flush)) {
      return fileSystem.openSync(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(),
          createNew.toBoolean(), flush.toBoolean());
    }
    else if (PhpTypes.notNull(perms) && PhpTypes.notNull(read) && PhpTypes.notNull(write)
        && PhpTypes.notNull(createNew)) {
      return fileSystem.openSync(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(),
          createNew.toBoolean());
    }
    else if (PhpTypes.notNull(perms) && PhpTypes.notNull(createNew)) {
      return fileSystem.openSync(path.toString(), perms.toString(), createNew.toBoolean());
    }
    else if (PhpTypes.notNull(perms)) {
      return fileSystem.openSync(path.toString(), perms.toString());
    }
    else {
      return fileSystem.openSync(path.toString());
    }
  }

  /**
   * Executes an asynchronous props call.
   */
  public FileSystem props(Env env, StringValue path, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::props() must be callable.");
    fileSystem.props(path.toString(), new AsyncResultHandler<org.vertx.java.core.file.FileProps>(env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.file.FileProps, FileProps>() {
      @Override
      public FileProps wrap(org.vertx.java.core.file.FileProps props) {
        return new FileProps(props);
      }
    }));
    return this;
  }

  /**
   * Executes a synchronous props call.
   */
  public FileSystem propsSync(Env env, StringValue path) {
    fileSystem.propsSync(path.toString());
    return this;
  }

  /**
   * Executes an asynchronous readdir call.
   */
  public FileSystem readDir(Env env, StringValue path, @Optional StringValue filter, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::readDir() must be callable.");
    if (PhpTypes.notNull(filter)) {
      fileSystem.readDir(path.toString(), filter.toString(),
          new AsyncResultHandler<String[]>(env, PhpTypes.toCallable(handler)));
    }
    else {
      fileSystem.readDir(path.toString(), new AsyncResultHandler<String[]>(env, PhpTypes.toCallable(handler)));
    }
    return this;
  }

  /**
   * Executes a synchronous readdir call.
   */
  public String[] readDirSync(Env env, StringValue path, @Optional StringValue filter) {
    if (PhpTypes.notNull(filter)) {
      return fileSystem.readDirSync(path.toString(), filter.toString());
    }
    else {
      return fileSystem.readDirSync(path.toString());
    }
  }

  /**
   * Executes an asynchronous readfile call.
   */
  public FileSystem readFile(Env env, StringValue path, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::readFile() must be callable.");
    fileSystem.readFile(path.toString(), new AsyncResultHandler<org.vertx.java.core.buffer.Buffer>(env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.buffer.Buffer, Buffer>() {
      @Override
      public Buffer wrap(org.vertx.java.core.buffer.Buffer buffer) {
        return new Buffer(buffer);
      }
    }));
    return this;
  }

  /**
   * Executes a synchronous readfile call.
   */
  public FileSystem readFileSync(Env env, StringValue path) {
    fileSystem.readFileSync(path.toString());
    return this;
  }

  /**
   * Executes an asynchronous read symlink call.
   */
  public FileSystem readSymlink(Env env, StringValue link, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\File\\FileSystem::readSymlink() must be callable.");
    fileSystem.readSymlink(link.toString(), new AsyncResultHandler<String>(env, PhpTypes.toCallable(handler)));
    return this;
  }

  /**
   * Executes a synchronous read symlink call.
   */
  public String readSymlinkSync(Env env, StringValue link) {
    return fileSystem.readSymlinkSync(link.toString());
  }

  /**
   * Executes an asynchronous symlink call.
   */
  public FileSystem symlink(Env env, StringValue link, StringValue existing, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::symlink() must be callable.");
    fileSystem.symlink(link.toString(), existing.toString(),
        HandlerFactory.createAsyncVoidHandler(env, handler));
    return this;
  }

  /**
   * Executes a synchronous symlink call.
   */
  public FileSystem symlinkSync(Env env, StringValue link, StringValue existing) {
    fileSystem.symlinkSync(link.toString(), existing.toString());
    return this;
  }

  /**
   * Executes an asynchronous truncate call.
   */
  public FileSystem truncate(Env env, StringValue path, NumberValue len, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::truncate() must be callable.");
    fileSystem.truncate(path.toString(), len.toLong(), HandlerFactory.createAsyncVoidHandler(env, handler));
    return this;
  }

  /**
   * Executes a synchronous truncate call.
   */
  public FileSystem truncateSync(Env env, StringValue path, NumberValue len) {
    fileSystem.truncateSync(path.toString(), len.toLong());
    return this;
  }

  /**
   * Executes an asynchronous unlink call.
   */
  public FileSystem unlink(Env env, StringValue link, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::unlink() must be callable.");
    fileSystem.unlink(link.toString(), HandlerFactory.createAsyncVoidHandler(env, handler));
    return this;
  }

  /**
   * Executes a synchronous unlink call.
   */
  public FileSystem unlinkSync(Env env, StringValue link) {
    fileSystem.unlinkSync(link.toString());
    return this;
  }

  /**
   * Executes an asynchronous write file call.
   */
  public FileSystem writeFile(Env env, StringValue path, Buffer buffer, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\FileSystem::writeFile() must be callable.");
    fileSystem.writeFile(path.toString(), buffer.__toVertxBuffer(), HandlerFactory.createAsyncVoidHandler(env, handler));
    return this;
  }

  /**
   * Executes a synchronous write file call.
   */
  public FileSystem writeFileSync(Env env, StringValue path, Buffer buffer) {
    fileSystem.writeFileSync(path.toString(), buffer.__toVertxBuffer());
    return this;
  }

  public String toString() {
    return "php:Vertx\\FileSystem";
  }

}

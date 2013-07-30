package com.blankstyle.vertx.php.file;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.file.FileProps;
import org.vertx.java.core.file.FileSystemProps;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;

/**
 * A PHP compatible implementation of the Vert.x FileSystem.
 */
public final class FileSystem {

  private org.vertx.java.core.file.FileSystem fileSystem;

  public FileSystem(org.vertx.java.core.file.FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  public FileSystem chmod(final Env env, StringValue path, StringValue perms, @Optional StringValue dirPerms, final Callback handler) {
    if (dirPerms != null && !dirPerms.isDefault()) {
      fileSystem.chmod(path.toString(), perms.toString(), dirPerms.toString(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      fileSystem.chmod(path.toString(), perms.toString(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    return this;
  }

  public FileSystem chmodSync(Env env, StringValue path, StringValue perms, @Optional StringValue dirPerms) {
    if (dirPerms != null && !dirPerms.isDefault()) {
      fileSystem.chmodSync(path.toString(), perms.toString(), dirPerms.toString());
    }
    else {
      fileSystem.chmodSync(path.toString(), perms.toString());
    }
    return this;
  }

  public FileSystem copy(final Env env, StringValue from, StringValue to, @Optional BooleanValue recursive, final Callback handler) {
    if (recursive != null && !recursive.isDefault()) {
      fileSystem.copy(from.toString(), to.toString(), recursive.toBoolean(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      fileSystem.copy(from.toString(), to.toString(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    return this;
  }

  public FileSystem copySync(Env env, StringValue from, StringValue to, @Optional BooleanValue recursive) {
    if (recursive != null && !recursive.isDefault()) {
      fileSystem.copySync(from.toString(), to.toString(), recursive.toBoolean());
    }
    else {
      fileSystem.copySync(from.toString(), to.toString());
    }
    return this;
  }

  public FileSystem createFile(final Env env, StringValue path, @Optional StringValue perms, final Callback handler) {
    if (perms != null && !perms.isDefault()) {
      fileSystem.createFile(path.toString(), perms.toString(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      fileSystem.createFile(path.toString(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    return this;
  }

  public FileSystem createFileSync(Env env, StringValue path, @Optional StringValue perms) {
    if (perms != null && !perms.isDefault()) {
      fileSystem.createFileSync(path.toString(), perms.toString());
    }
    else {
      fileSystem.createFileSync(path.toString());
    }
    return this;
  }

  public FileSystem delete(final Env env, StringValue path, @Optional BooleanValue recursive, final Callback handler) {
    if (recursive != null && !recursive.isDefault()) {
      fileSystem.delete(path.toString(), recursive.toBoolean(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      fileSystem.delete(path.toString(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    return this;
  }

  public FileSystem deleteSync(Env env, StringValue path, @Optional BooleanValue recursive) {
    if (recursive != null && !recursive.isDefault()) {
      fileSystem.deleteSync(path.toString(), recursive.toBoolean());
    }
    else {
      fileSystem.deleteSync(path.toString());
    }
    return this;
  }

  public FileSystem exists(final Env env, StringValue path, final Callback handler) {
    fileSystem.exists(path.toString(), new AsyncResultHandler<Boolean>() {
      @Override
      public void handle(AsyncResult<Boolean> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public boolean existsSync(Env env, StringValue path) {
    return fileSystem.existsSync(path.toString());
  }

  public FileSystem fsProps(final Env env, StringValue path, final Callback handler) {
    fileSystem.fsProps(path.toString(), new AsyncResultHandler<FileSystemProps>() {
      @Override
      public void handle(AsyncResult<FileSystemProps> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystemProps fsPropsSync(Env env, StringValue path) {
    return fileSystem.fsPropsSync(path.toString());
  }

  public FileSystem link(final Env env, StringValue link, StringValue existing, final Callback handler) {
    fileSystem.link(link.toString(), existing.toString(), new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystem linkSync(Env env, StringValue link, StringValue existing) {
    fileSystem.linkSync(link.toString(), existing.toString());
    return this;
  }

  public FileSystem lprops(final Env env, StringValue path, final Callback handler) {
    fileSystem.lprops(path.toString(), new AsyncResultHandler<FileProps>() {
      @Override
      public void handle(AsyncResult<FileProps> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystem lpropsSync(Env env, StringValue path) {
    fileSystem.lpropsSync(path.toString());
    return this;
  }

  public FileSystem mkdir(final Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue createParents, final Callback handler) {
    if (perms != null && !perms.isDefault()) {
      if (createParents != null && !createParents.isDefault()) {
        fileSystem.mkdir(path.toString(), perms.toString(), createParents.toBoolean(), new AsyncResultHandler<Void>() {
          @Override
          public void handle(AsyncResult<Void> result) {
            handler.call(env, env.wrapJava(result));
          }
        });
      }
      else {
        fileSystem.mkdir(path.toString(), perms.toString(), new AsyncResultHandler<Void>() {
          @Override
          public void handle(AsyncResult<Void> result) {
            handler.call(env, env.wrapJava(result));
          }
        });
      }
    }
    else if (createParents != null && !createParents.isDefault()) {
      fileSystem.mkdir(path.toString(), createParents.toBoolean(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      fileSystem.mkdir(path.toString(), new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    return this;
  }

  public FileSystem mkdirSync(Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue createParents) {
    if (perms != null && !perms.isDefault()) {
      if (createParents != null && !createParents.isDefault()) {
        fileSystem.mkdirSync(path.toString(), perms.toString(), createParents.toBoolean());
      }
      else {
        fileSystem.mkdirSync(path.toString(), perms.toString());
      }
    }
    else if (createParents != null && !createParents.isDefault()) {
      fileSystem.mkdirSync(path.toString(), createParents.toBoolean());
    }
    else {
      fileSystem.mkdirSync(path.toString());
    }
    return this;
  }

  public FileSystem move(final Env env, StringValue from, StringValue to, final Callback handler) {
    fileSystem.move(from.toString(), to.toString(), new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystem moveSync(Env env, StringValue from, StringValue to) {
    fileSystem.moveSync(from.toString(), to.toString());
    return this;
  }

  public FileSystem open(final Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue read, @Optional BooleanValue write, @Optional BooleanValue createNew, @Optional BooleanValue flush, final Callback handler) {
    if ((perms != null && !perms.isDefault()) && (read != null && !read.isDefault()) && (write != null && !write.isDefault()) && (createNew != null && !createNew.isDefault()) && (flush != null && !flush.isDefault())) {
      fileSystem.open(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean(), flush.toBoolean(), new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>() {
        public void handle(AsyncResult<org.vertx.java.core.file.AsyncFile> file) {
          handler.call(env, env.wrapJava(file));
        }
      });
    }
    else if ((perms != null && !perms.isDefault()) && (read != null && !read.isDefault()) && (write != null && !write.isDefault()) && (createNew != null && !createNew.isDefault())) {
      fileSystem.open(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean(), new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>() {
        public void handle(AsyncResult<org.vertx.java.core.file.AsyncFile> file) {
          handler.call(env, env.wrapJava(file));
        }
      });
    }
    else if ((perms != null && !perms.isDefault()) && (createNew != null && !createNew.isDefault())) {
      fileSystem.open(path.toString(), perms.toString(), createNew.toBoolean(), new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>() {
        public void handle(AsyncResult<org.vertx.java.core.file.AsyncFile> file) {
          handler.call(env, env.wrapJava(file));
        }
      });
    }
    else if (perms != null && !perms.isDefault()) {
      fileSystem.open(path.toString(), perms.toString(), new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>() {
        public void handle(AsyncResult<org.vertx.java.core.file.AsyncFile> file) {
          handler.call(env, env.wrapJava(file));
        }
      });
    }
    else {
      fileSystem.open(path.toString(), new AsyncResultHandler<org.vertx.java.core.file.AsyncFile>() {
        public void handle(AsyncResult<org.vertx.java.core.file.AsyncFile> file) {
          handler.call(env, env.wrapJava(file));
        }
      });
    }
    return this;
  }

  public org.vertx.java.core.file.AsyncFile openSync(Env env, StringValue path, @Optional StringValue perms, @Optional BooleanValue read, @Optional BooleanValue write, @Optional BooleanValue createNew, @Optional BooleanValue flush) {
    if ((perms != null && !perms.isDefault()) && (read != null && !read.isDefault()) && (write != null && !write.isDefault()) && (createNew != null && !createNew.isDefault()) && (flush != null && !flush.isDefault())) {
      return fileSystem.openSync(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean(), flush.toBoolean());
    }
    else if ((perms != null && !perms.isDefault()) && (read != null && !read.isDefault()) && (write != null && !write.isDefault()) && (createNew != null && !createNew.isDefault())) {
      return fileSystem.openSync(path.toString(), perms.toString(), read.toBoolean(), write.toBoolean(), createNew.toBoolean());
    }
    else if ((perms != null && !perms.isDefault()) && (createNew != null && !createNew.isDefault())) {
      return fileSystem.openSync(path.toString(), perms.toString(), createNew.toBoolean());
    }
    else if (perms != null && !perms.isDefault()) {
      return fileSystem.openSync(path.toString(), perms.toString());
    }
    else {
      return fileSystem.openSync(path.toString());
    }
  }

  public FileSystem props(final Env env, StringValue path, final Callback handler) {
    fileSystem.props(path.toString(), new AsyncResultHandler<FileProps>() {
      @Override
      public void handle(AsyncResult<FileProps> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystem propsSync(Env env, StringValue path) {
    fileSystem.propsSync(path.toString());
    return this;
  }

  public FileSystem readDir(final Env env, StringValue path, @Optional StringValue filter, final Callback handler) {
    if (filter != null && !filter.isDefault()) {
      fileSystem.readDir(path.toString(), filter.toString(), new AsyncResultHandler<String[]>() {
        @Override
        public void handle(AsyncResult<String[]> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      fileSystem.readDir(path.toString(), new AsyncResultHandler<String[]>() {
        @Override
        public void handle(AsyncResult<String[]> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    return this;
  }

  public String[] readDirSync(Env env, StringValue path, @Optional StringValue filter) {
    if (filter != null && !filter.isDefault()) {
      return fileSystem.readDirSync(path.toString(), filter.toString());
    }
    else {
      return fileSystem.readDirSync(path.toString());
    }
  }

  public FileSystem readFile(final Env env, StringValue path, final Callback handler) {
    fileSystem.readFile(path.toString(), new AsyncResultHandler<Buffer>() {
      @Override
      public void handle(AsyncResult<Buffer> buffer) {
        handler.call(env, env.wrapJava(buffer));
      }
    });
    return this;
  }

  public FileSystem readFileSync(Env env, StringValue path) {
    fileSystem.readFileSync(path.toString());
    return this;
  }

  public FileSystem readSymlink(final Env env, StringValue link, final Callback handler) {
    fileSystem.readSymlink(link.toString(), new AsyncResultHandler<String>() {
      @Override
      public void handle(AsyncResult<String> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public String readSymlinkSync(Env env, StringValue link) {
    return fileSystem.readSymlinkSync(link.toString());
  }

  public FileSystem symlink(final Env env, StringValue link, StringValue existing, final Callback handler) {
    fileSystem.symlink(link.toString(), existing.toString(), new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystem symlinkSync(Env env, StringValue link, StringValue existing) {
    fileSystem.symlinkSync(link.toString(), existing.toString());
    return this;
  }

  public FileSystem truncate(final Env env, StringValue path, NumberValue len, final Callback handler) {
    fileSystem.truncate(path.toString(), len.toLong(), new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystem truncateSync(Env env, StringValue path, NumberValue len) {
    fileSystem.truncateSync(path.toString(), len.toLong());
    return this;
  }

  public FileSystem unlink(final Env env, StringValue link, final Callback handler) {
    fileSystem.unlink(link.toString(), new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystem unlinkSync(Env env, StringValue link) {
    fileSystem.unlinkSync(link.toString());
    return this;
  }

  public FileSystem writeFile(final Env env, StringValue path, Buffer data, final Callback handler) {
    fileSystem.writeFile(path.toString(), data, new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  public FileSystem writeFileSync(Env env, StringValue path, Buffer data) {
    fileSystem.writeFileSync(path.toString(), data);
    return this;
  }

}

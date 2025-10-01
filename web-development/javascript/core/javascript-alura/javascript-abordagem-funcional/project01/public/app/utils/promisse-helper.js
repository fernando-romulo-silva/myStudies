export const handleStatus = (res) =>
  res.ok ? res.json() : Promise.reject(res.statusText);

export const log = (param) => {
  console.log(param);
  return param;
};

export const timeoutPromise = (milliSeconds, promise) => {
  const timeout = new Promise((resolve, reject) =>
    setTimeout(
      () => reject(`Limite da promise excedido (limite: ${milliSeconds} ms)`),
      milliSeconds
    )
  );

  return Promise.race([timeout, promise]);
};

export const delay = (milliSeconds) => (data) =>
  new Promise((resolve, reject) =>
    setTimeout(() => resolve(data), milliSeconds)
  );

export const retry = (retries, milliseconds, fn) =>
  fn().catch((err) => {
    console.log(retries);
    return delay(milliseconds)().then(() =>
      retries > 1 ? retry(retries - 1, milliseconds, fn) : Promise.reject(err)
    );
  });

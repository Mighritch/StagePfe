import { TestBed } from '@angular/core/testing';

import { EffectifParAdmtech } from './effectif-par-admtech';

describe('EffectifParAdmtech', () => {
  let service: EffectifParAdmtech;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EffectifParAdmtech);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
